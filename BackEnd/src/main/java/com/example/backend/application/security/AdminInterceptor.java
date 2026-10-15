package com.example.backend.application.security;

import com.example.backend.domain.exception.InvalidCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    public static final String ADMIN_HEADER = "X-Admin-Token";

    private final String adminToken;

    // Sem valor padrão: se ADMIN_TOKEN não estiver configurado, a aplicação nem sobe.
    public AdminInterceptor(@Value("${app.admin-token}") String adminToken) {
        this.adminToken = adminToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod method) || !isAdminOnly(method)) {
            return true;
        }

        String received = request.getHeader(ADMIN_HEADER);

        // Comparação em tempo constante
        boolean valid = received != null && MessageDigest.isEqual(
                received.getBytes(StandardCharsets.UTF_8),
                adminToken.getBytes(StandardCharsets.UTF_8));

        if (!valid) {
            throw new InvalidCredentialsException();
        }
        return true;
    }

    private boolean isAdminOnly(HandlerMethod method) {
        return method.hasMethodAnnotation(AdminOnly.class)
                || method.getBeanType().isAnnotationPresent(AdminOnly.class);
    }
}
