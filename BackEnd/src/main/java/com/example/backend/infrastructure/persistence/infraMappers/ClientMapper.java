package com.example.backend.infrastructure.persistence.infraMappers;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.infrastructure.persistence.ClientDocument;

public class ClientMapper {
    public static ClientEntity toDomain(ClientDocument document){
        return new ClientEntity(
                document.getId(),
                document.getName(),
                document.getDocument(),
                document.getDocumentType(),
                document.getPlanType(),
                document.getBalance(),
                document.getCredit_limit(),
                document.isActive()
        );
    }
    
    public static ClientDocument toDocument(ClientEntity entity){


        return new ClientDocument(
                entity.getId(),
                entity.getName(),
                entity.getDocument(),
                entity.getDocumentType(),
                entity.getPlanType(),
                entity.getBalance(),
                entity.getCreditLimit(),
                entity.isActive()
        );
    }
}
