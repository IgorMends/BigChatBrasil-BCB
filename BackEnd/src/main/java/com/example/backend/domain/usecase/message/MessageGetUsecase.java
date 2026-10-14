package com.example.backend.domain.usecase.message;

import com.example.backend.application.DTO.apiMappers.MessageApiMapper;
import com.example.backend.application.DTO.response.message.MessageResponse;
import com.example.backend.domain.exception.ResourceNotFoundException;
import com.example.backend.domain.port.MessageRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageGetUsecase {

    private final MessageRepository messageRepository;

    public MessageResponse execute(UUID clientId, UUID messageId) {
        return messageRepository.findByIdAndClientId(messageId, clientId)
                .map(MessageApiMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Mensagem não encontrada"));
    }
}