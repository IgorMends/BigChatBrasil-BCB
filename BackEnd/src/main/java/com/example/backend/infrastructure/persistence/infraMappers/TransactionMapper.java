package com.example.backend.infrastructure.persistence.infraMappers;

import com.example.backend.domain.entity.TransactionEntity;
import com.example.backend.domain.entity.enums.TransactionType;
import com.example.backend.infrastructure.persistence.transaction.TransactionDocument;

public class TransactionMapper {

    private TransactionMapper() {
    }

    public static TransactionEntity toDomain(TransactionDocument document) {
        return new TransactionEntity(
                document.getId(),
                document.getClientId(),
                document.getMessageId(),
                TransactionType.valueOf(document.getType().toUpperCase()),
                document.getAmount(),
                document.getCreatedAt());
    }

    public static TransactionDocument toDocument(TransactionEntity transaction) {
        return new TransactionDocument(
                transaction.getId(),
                transaction.getClientId(),
                transaction.getMessageId(),
                toDb(transaction.getType()),
                transaction.getAmount(),
                transaction.getCreatedAt());
    }

    public static String toDb(TransactionType type) {
        return type == null ? null : type.name().toLowerCase();
    }
}

