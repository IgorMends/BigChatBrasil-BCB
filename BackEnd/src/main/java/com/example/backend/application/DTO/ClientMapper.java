package com.example.backend.application.DTO;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.infrastructure.persistence.ClientDocument;

import java.util.UUID;

public class ClientMapper {
    public static ClientEntity toDomain(ClientDocument document){
        return new ClientEntity(
                document.getId().toString(),
                document.getName(),
                document.getDocument(),
                document.getDocumentType(),
                document.getPlanType(),
                document.getBalance(),
                document.getLimit(),
                document.isActive()
        );
    }
    
    public static ClientDocument toDocument(ClientEntity entity){

        UUID uuid = UUID.fromString(entity.getId());

        return new ClientDocument(
                uuid,
                entity.getName(),
                entity.getDocument(),
                entity.getDocumentType(),
                entity.getPlanType(),
                entity.getBalance(),
                entity.getLimit(),
                entity.isActive()
        );
    }
}
