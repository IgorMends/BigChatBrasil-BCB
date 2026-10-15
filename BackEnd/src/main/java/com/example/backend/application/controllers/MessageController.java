package com.example.backend.application.controllers;

import com.example.backend.application.DTO.request.message.SendMessageRequestDTO;
import com.example.backend.application.DTO.response.message.MessageResponseDTO;
import com.example.backend.application.DTO.response.message.MessageStatusResponseDTO;
import com.example.backend.application.DTO.response.message.SendMessageResponseDTO;
import com.example.backend.application.security.CurrentClient;
import com.example.backend.domain.usecase.message.MessageGetStatusUsecase;
import com.example.backend.domain.usecase.message.MessageGetUsecase;
import com.example.backend.domain.usecase.message.MessageListByConversationUsecase;
import com.example.backend.domain.usecase.message.MessageSendUsecase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageSendUsecase sendUsecase;
    private final MessageGetUsecase getUsecase;
    private final MessageGetStatusUsecase getStatusUsecase;
    private final MessageListByConversationUsecase listByConversationUsecase;

    @PostMapping
    public ResponseEntity<SendMessageResponseDTO> send(@CurrentClient UUID clientId,
                                                       @RequestBody @Valid SendMessageRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sendUsecase.execute(clientId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> get(@CurrentClient UUID clientId, @PathVariable UUID id) {
        return ResponseEntity.ok(getUsecase.execute(clientId, id));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<MessageStatusResponseDTO> getStatus(@CurrentClient UUID clientId, @PathVariable UUID id) {
        return ResponseEntity.ok(getStatusUsecase.execute(clientId, id));
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<MessageResponseDTO>> listByConversation(
            @CurrentClient UUID clientId,
            @PathVariable UUID conversationId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(listByConversationUsecase.execute(clientId, conversationId, page, size));
    }
}