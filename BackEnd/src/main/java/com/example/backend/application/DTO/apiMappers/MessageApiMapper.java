package com.example.backend.application.DTO.apiMappers;

import com.example.backend.application.DTO.response.message.MessageResponse;
import com.example.backend.application.DTO.response.message.MessageStatusResponse;
import com.example.backend.domain.entity.MessageEntity;

public class MessageApiMapper {

    private MessageApiMapper() {
    }

    public static MessageResponse toResponse(MessageEntity message) {
        return new MessageResponse(
                message.getId(),
                message.getConversationId(),
                message.getSenderId(),
                message.getSenderType(),
                message.getRecipientId(),
                message.getContent(),
                message.getCreatedAt(),
                lower(message.getPriority()),
                lower(message.getStatus()),
                message.getCost());
    }

    public static MessageStatusResponse toStatusResponse(MessageEntity message) {
        return new MessageStatusResponse(message.getId(), lower(message.getStatus()));
    }

    // A API expõe em minúsculas (normal, queued...), como no enunciado
    public static String lower(Enum<?> value) {
        return value == null ? null : value.name().toLowerCase();
    }
}