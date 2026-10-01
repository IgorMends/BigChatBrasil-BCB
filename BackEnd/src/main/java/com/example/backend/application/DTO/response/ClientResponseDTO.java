package com.example.backend.application.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {

    private UUID id;
    private String name;
    private String document;
    private String documentType;
    private String planType;
    private float balance;
    private float creditLimit;
    private boolean active;
}