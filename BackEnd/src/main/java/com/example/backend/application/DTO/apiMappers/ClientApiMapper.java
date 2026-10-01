package com.example.backend.application.DTO.apiMappers;

import com.example.backend.application.DTO.request.ClientRequestDTO;
import com.example.backend.application.DTO.response.ClientResponseDTO;
import com.example.backend.domain.entity.ClientEntity;

public class ClientApiMapper {

    public static ClientEntity toDomain(ClientRequestDTO dto) {
        return ClientEntity.builder()
                .name(dto.getName())
                .document(dto.getDocument())
                .documentType(dto.getDocumentType())
                .planType(dto.getPlanType())
                .balance(dto.getBalance())
                .creditLimit(dto.getCreditLimit())
                .active(dto.isActive())
                .build();
    }

    public static ClientResponseDTO toSaveResponseDTO(ClientEntity entity) {
        return ClientResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static ClientResponseDTO toFindResponseDTO(ClientEntity entity) {
        return ClientResponseDTO.builder()
                .name(entity.getName())
                .document(entity.getDocument())
                .documentType(entity.getDocumentType())
                .planType(entity.getPlanType())
                .balance(entity.getBalance())
                .creditLimit(entity.getCreditLimit())
                .active(entity.isActive())
                .build();
    }
}