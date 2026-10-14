package com.example.backend.domain.usecase.message;

import com.example.backend.application.DTO.apiMappers.MessageApiMapper;
import com.example.backend.application.DTO.response.message.MessageStatusResponse;
import com.example.backend.domain.exception.ResourceNotFoundException;
import com.example.backend.domain.port.MessageRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageGetStatusUsecase {

    private final MessageRepository messageRepository;

    public MessageStatusResponse execute(UUID clientId, UUID messageId) {
        return messageRepository.findByIdAndClientId(messageId, clientId)
                .map(MessageApiMapper::toStatusResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Mensagem não encontrada"));
    }
}