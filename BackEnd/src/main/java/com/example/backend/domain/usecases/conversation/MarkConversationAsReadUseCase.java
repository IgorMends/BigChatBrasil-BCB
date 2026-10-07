package com.example.backend.domain.usecases.conversation;

import com.example.backend.application.DTO.apiMappers.ConversationApiMapper;
import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.exceptions.ConversationNotFoundException;
import com.example.backend.domain.port.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MarkConversationAsReadUseCase {

    private final ConversationRepository conversationRepository;

    public MarkConversationAsReadUseCase(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public ConversationResponseDTO execute(UUID conversationId, UUID clientId) {
        ConversationEntity conversation = conversationRepository.findByIdAndClientId(conversationId, clientId)
                .orElseThrow(() -> new ConversationNotFoundException(conversationId));

        conversation.markAsRead();
        return ConversationApiMapper.toResponseDTO(conversationRepository.save(conversation));
    }
}