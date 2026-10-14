package com.example.backend.domain.exception;

// Se você já tem uma exceção de "não encontrado", use a sua e apague esta.
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}