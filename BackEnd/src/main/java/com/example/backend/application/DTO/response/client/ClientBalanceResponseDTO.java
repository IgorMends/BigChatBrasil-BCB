
package com.example.backend.application.DTO.response.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientBalanceResponseDTO {

    private String planType;

    private BigDecimal balance;

    private BigDecimal creditLimit;
    private BigDecimal consumed;
    private BigDecimal remainingLimit;
}
