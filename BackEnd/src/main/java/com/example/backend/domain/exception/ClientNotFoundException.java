package com.example.backend.domain.exception;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(UUID id) {
        super("Cliente não encontrado com id: " + id);
    }
}