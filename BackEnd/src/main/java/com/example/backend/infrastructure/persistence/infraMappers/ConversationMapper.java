package com.example.backend.infrastructure.persistence.infraMappers;

import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.infrastructure.persistence.conversation.ConversationDocument;

public class ConversationMapper {

    public static ConversationEntity toDomain(ConversationDocument document) {
        return new ConversationEntity(
                document.getId(),
                document.getClientId(),
                document.getRecipientId(),
                document.getRecipientName(),
                document.getRecipientPhoneNumber(),
                document.getLastMessageContent(),
                document.getLastMessageTime(),
                document.getUnreadCount()
        );
    }

    public static ConversationDocument toDocument(ConversationEntity entity) {
        return new ConversationDocument(
                entity.getId(),
                entity.getClientId(),
                entity.getRecipientId(),
                entity.getRecipientName(),
                entity.getRecipientPhoneNumber(),
                entity.getLastMessageContent(),
                entity.getLastMessageTime(),
                entity.getUnreadCount()
        );
    }
}