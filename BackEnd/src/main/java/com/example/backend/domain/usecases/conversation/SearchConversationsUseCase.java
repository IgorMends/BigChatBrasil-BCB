package com.example.backend.domain.usecases.conversation;

import com.example.backend.application.DTO.apiMappers.ConversationApiMapper;
import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.port.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SearchConversationsUseCase {

    private final ConversationRepository conversationRepository;

    public SearchConversationsUseCase(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<ConversationResponseDTO> execute(UUID clientId, String search) {
        if (search == null || search.isBlank()) {
            return ConversationApiMapper.toResponseDTOList(conversationRepository.findAllByClientId(clientId));
        }
        return ConversationApiMapper.toResponseDTOList(conversationRepository.searchByClientIdAndRecipientName(clientId, search.trim()));
    }
}