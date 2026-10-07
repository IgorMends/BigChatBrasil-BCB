package com.example.backend.domain.usecases.conversation;

import com.example.backend.application.DTO.apiMappers.ConversationApiMapper;
import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.exceptions.ConversationNotFoundException;
import com.example.backend.domain.port.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetConversationUseCase {

    private final ConversationRepository conversationRepository;


    public ConversationResponseDTO execute(UUID conversationId, UUID clientId) {
        return ConversationApiMapper.toResponseDTO(conversationRepository.findByIdAndClientId(conversationId, clientId)
                .orElseThrow(() -> new ConversationNotFoundException(conversationId)));
    }
}
