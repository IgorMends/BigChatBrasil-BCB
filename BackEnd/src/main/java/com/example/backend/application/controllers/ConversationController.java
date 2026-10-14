package com.example.backend.application.controllers;

import com.example.backend.application.DTO.request.conversation.ConversationCreateRequestDTO;
import com.example.backend.application.DTO.response.conversation.ConversationResponseDTO;
import com.example.backend.domain.usecase.conversation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ListConversationsUseCase listConversationsUseCase;
    private final SearchConversationsUseCase searchConversationsUseCase;
    private final GetConversationUseCase getConversationUseCase;
    private final MarkConversationAsReadUseCase markConversationAsReadUseCase;
    private final CreateConversationUseCase createConversationUseCase;

    @GetMapping
    public ResponseEntity<List<ConversationResponseDTO>> list(
            @RequestParam UUID clientId,
            @RequestParam(required = false) String search) {

        List<ConversationResponseDTO> response = (search == null || search.isBlank())
                ? listConversationsUseCase.execute(clientId)
                : searchConversationsUseCase.execute(clientId, search);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponseDTO> findById(
            @PathVariable UUID id,
            @RequestParam UUID clientId) {

        ConversationResponseDTO response = getConversationUseCase.execute(id, clientId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ConversationResponseDTO> create(
            @RequestParam UUID clientId,
            @Valid @RequestBody ConversationCreateRequestDTO dto) {

        ConversationResponseDTO response = createConversationUseCase.execute(clientId, dto);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ConversationResponseDTO> markAsRead(
            @PathVariable UUID id,
            @RequestParam UUID clientId) {

        ConversationResponseDTO response = markConversationAsReadUseCase.execute(id, clientId);

        return ResponseEntity.ok(response);
    }
}