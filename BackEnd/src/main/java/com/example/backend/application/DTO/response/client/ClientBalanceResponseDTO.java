
package com.example.backend.application.DTO.response.client;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientBalanceResponseDTO {
    private BigDecimal balance;
}
