package com.example.backend.domain.usecases.conversation;

import com.example.backend.application.DTO.apiMappers.ConversationApiMapper;
import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.port.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListConversationsUseCase {

    private final ConversationRepository conversationRepository;

    public List<ConversationResponseDTO> execute(UUID clientId) {
        return ConversationApiMapper.toResponseDTOList(conversationRepository.findAllByClientId(clientId));
    }
}
