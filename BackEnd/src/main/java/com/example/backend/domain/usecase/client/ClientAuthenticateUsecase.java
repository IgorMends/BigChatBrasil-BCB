package com.example.backend.domain.usecase.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.request.client.ClientAuthRequestDTO;
import com.example.backend.application.DTO.response.client.ClientAuthResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.exception.InvalidCredentialsException;
import com.example.backend.domain.port.ClientRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAuthenticateUsecase {

    private final ClientRepository clientRepository;

    public ClientAuthResponseDTO authenticate(ClientAuthRequestDTO request) {
        ClientEntity client = clientRepository.findByDocument(request.getDocument().trim())
                .orElseThrow(InvalidCredentialsException::new);

        boolean sameType = client.getDocumentType() != null
                && client.getDocumentType().equalsIgnoreCase(request.getDocumentType().trim());

        if (!client.isActive() || !sameType) {
            throw new InvalidCredentialsException();
        }

        return ClientApiMapper.toAuthResponseDTO(client);
    }

    public UUID resolveClientId(String document) {
        if (document == null || document.isBlank()) {
            throw new InvalidCredentialsException();
        }

        return clientRepository.findByDocument(document.trim())
                .filter(ClientEntity::isActive)
                .map(ClientEntity::getId)
                .orElseThrow(InvalidCredentialsException::new);
    }
}
