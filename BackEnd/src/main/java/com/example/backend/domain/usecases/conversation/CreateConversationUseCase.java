package com.example.backend.domain.usecases.conversation;

import com.example.backend.application.DTO.apiMappers.ConversationApiMapper;
import com.example.backend.application.DTO.request.conversation.ConversationCreateRequestDTO;
import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.port.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateConversationUseCase {

    private final ConversationRepository conversationRepository;

    public CreateConversationUseCase(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public ConversationResponseDTO execute(UUID clientId, ConversationCreateRequestDTO dto) {
        return ConversationApiMapper.toResponseDTO(conversationRepository.findByClientIdAndRecipientId(clientId, dto.getRecipientId())
                .orElseGet(() -> conversationRepository.save(
                        ConversationEntity.builder()
                                .id(UUID.randomUUID())
                                .clientId(clientId)
                                .recipientId(dto.getRecipientId())
                                .recipientName(dto.getRecipientName())
                                .recipientPhoneNumber(dto.getRecipientPhoneNumber())
                                .unreadCount(0)
                                .build())));
    }
}