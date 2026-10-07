package com.example.backend.application.DTO.apiMappers;

import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;

import java.util.List;

public class ConversationApiMapper {

    public static ConversationResponseDTO toResponseDTO(ConversationEntity entity) {
        return ConversationResponseDTO.builder()
                .id(entity.getId())
                .recipientId(entity.getRecipientId())
                .recipientName(entity.getRecipientName())
                .recipientPhoneNumber(entity.getRecipientPhoneNumber())
                .lastMessageContent(entity.getLastMessageContent())
                .lastMessageTime(entity.getLastMessageTime())
                .unreadCount(entity.getUnreadCount())
                .build();
    }

    public static List<ConversationResponseDTO> toResponseDTOList(List<ConversationEntity> entities) {
        return entities.stream()
                .map(ConversationApiMapper::toResponseDTO)
                .toList();
    }
}
