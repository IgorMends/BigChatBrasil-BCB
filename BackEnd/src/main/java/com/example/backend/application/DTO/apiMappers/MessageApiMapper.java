package com.example.backend.application.DTO.apiMappers;

import com.example.backend.application.DTO.response.message.MessageResponseDTO;
import com.example.backend.application.DTO.response.message.MessageStatusResponseDTO;
import com.example.backend.domain.entity.MessageEntity;

public class MessageApiMapper {

    private MessageApiMapper() {
    }

    public static MessageResponseDTO toResponse(MessageEntity message) {
        return new MessageResponseDTO(
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

    public static MessageStatusResponseDTO toStatusResponse(MessageEntity message) {
        return new MessageStatusResponseDTO(message.getId(), lower(message.getStatus()));
    }

    public static String lower(Enum<?> value) {
        return value == null ? null : value.name().toLowerCase();
    }
}