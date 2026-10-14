package com.example.backend.domain.usecase.message;

import com.example.backend.application.DTO.apiMappers.MessageApiMapper;
import com.example.backend.application.DTO.response.message.MessageResponse;
import com.example.backend.domain.exception.ResourceNotFoundException;
import com.example.backend.domain.port.ConversationRepository;
import com.example.backend.domain.port.MessageRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageListByConversationUsecase {

    private static final int MAX_PAGE_SIZE = 100;

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public List<MessageResponse> execute(UUID clientId, UUID conversationId, int page, int size) {
        // 404 quando a conversa não existe ou não é do cliente (em vez de devolver lista vazia)
        conversationRepository.findByIdAndClientId(conversationId, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa não encontrada"));

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);

        return messageRepository.findByConversationIdAndClientId(conversationId, clientId, safePage, safeSize)
                .stream()
                .map(MessageApiMapper::toResponse)
                .toList();
    }
}