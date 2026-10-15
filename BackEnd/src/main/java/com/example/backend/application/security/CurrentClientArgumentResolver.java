package com.example.backend.application.security;


import com.example.backend.domain.usecase.client.ClientAuthenticateUsecase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentClientArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String DOCUMENT_HEADER = "X-Document-Id";

    private final ClientAuthenticateUsecase authenticateUsecase;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentClient.class)
                && UUID.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        return authenticateUsecase.resolveClientId(webRequest.getHeader(DOCUMENT_HEADER));
    }
}
