package com.example.backend.application.DTO.response.client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientFindResponseDTO {
    private String name;
    private String document;
    private String documentType;
    private String planType;
    private float balance;
    private float creditLimit;
    private boolean active;
}
