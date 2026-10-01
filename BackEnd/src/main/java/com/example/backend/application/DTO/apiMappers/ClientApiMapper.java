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

    public static ClientResponseDTO toResponseDTO(ClientEntity entity) {
        return ClientResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}