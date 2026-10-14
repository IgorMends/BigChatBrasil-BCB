package com.example.backend.application.controllers;

import com.example.backend.application.DTO.request.message.SendMessageRequest;
import com.example.backend.application.DTO.response.message.MessageResponse;
import com.example.backend.application.DTO.response.message.MessageStatusResponse;
import com.example.backend.application.DTO.response.message.SendMessageResponse;
import com.example.backend.domain.usecase.message.MessageGetStatusUsecase;
import com.example.backend.domain.usecases.message.MessageGetUsecase;
import com.example.backend.domain.usecase.message.MessageListByConversationUsecase;
import com.example.backend.domain.usecases.message.MessageSendUsecase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO segurança: o clientId vem como parâmetro (Parte 1 do desafio). Trocar pela identificação via CPF/CNPJ depois.
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageSendUsecase sendUsecase;
    private final MessageGetUsecase getUsecase;
    private final MessageGetStatusUsecase getStatusUsecase;
    private final MessageListByConversationUsecase listByConversationUsecase;

    @PostMapping("/messages")
    public ResponseEntity<SendMessageResponse> send(@RequestParam UUID clientId,
                                                    @RequestBody @Valid SendMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sendUsecase.execute(clientId, request));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageResponse> get(@RequestParam UUID clientId, @PathVariable UUID id) {
        return ResponseEntity.ok(getUsecase.execute(clientId, id));
    }

    @GetMapping("/messages/{id}/status")
    public ResponseEntity<MessageStatusResponse> getStatus(@RequestParam UUID clientId, @PathVariable UUID id) {
        return ResponseEntity.ok(getStatusUsecase.execute(clientId, id));
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<MessageResponse>> listByConversation(
            @RequestParam UUID clientId,
            @PathVariable UUID conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(listByConversationUsecase.execute(clientId, conversationId, page, size));
    }
}