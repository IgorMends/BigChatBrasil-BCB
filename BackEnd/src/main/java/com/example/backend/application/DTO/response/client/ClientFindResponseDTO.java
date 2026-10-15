package com.example.backend.application.DTO.response.client;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private boolean active;
}
