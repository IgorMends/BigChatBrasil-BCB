package com.example.backend.application.DTO.response.client;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCreditAdjustResponseDTO {

    private UUID id;
    private String planType;
    private BigDecimal balance;
    private BigDecimal creditLimit;
}
