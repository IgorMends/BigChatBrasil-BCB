package com.example.backend.application.controllers;

import com.example.backend.application.DTO.request.client.ClientAuthRequestDTO;
import com.example.backend.application.DTO.response.client.ClientAuthResponseDTO;
import com.example.backend.domain.usecase.client.ClientAuthenticateUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientAuthenticateUsecase authenticateUsecase;

    @PostMapping
    public ResponseEntity<ClientAuthResponseDTO> authenticate(@RequestBody @Valid ClientAuthRequestDTO request) {
        return ResponseEntity.ok(authenticateUsecase.authenticate(request));
    }
}
