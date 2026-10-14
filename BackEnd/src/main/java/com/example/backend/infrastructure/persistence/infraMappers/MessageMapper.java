package com.example.backend.infrastructure.persistence.infraMappers;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.entity.enums.MessageStatus;
import com.example.backend.infrastructure.persistence.message.MessageDocument;

public class MessageMapper {

    private MessageMapper() {
    }

    public static MessageEntity toDomain(MessageDocument document) {
        return new MessageEntity(
                document.getId(),
                document.getConversationId(),
                document.getSenderId(),
                document.getSenderType(),
                document.getRecipientId(),
                document.getContent(),
                MessagePriority.valueOf(document.getPriority().toUpperCase()),
                MessageStatus.valueOf(document.getStatus().toUpperCase()),
                document.getCost(),
                document.getCreatedAt());
    }

    public static MessageDocument toDocument(MessageEntity message) {
        return new MessageDocument(
                message.getId(),
                message.getConversationId(),
                message.getSenderId(),
                message.getSenderType(),
                message.getRecipientId(),
                message.getContent(),
                toDb(message.getPriority()),
                toDb(message.getStatus()),
                message.getCost(),
                message.getCreatedAt());
    }

    public static String toDb(Enum<?> value) {
        return value == null ? null : value.name().toLowerCase();
    }
}