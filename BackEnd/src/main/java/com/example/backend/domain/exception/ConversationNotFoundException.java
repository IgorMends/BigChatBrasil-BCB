package com.example.backend.domain.exception;

import java.util.UUID;

public class ConversationNotFoundException extends RuntimeException {

    public ConversationNotFoundException(UUID id) {
        super("Conversa não encontrada: " + id);
    }
}