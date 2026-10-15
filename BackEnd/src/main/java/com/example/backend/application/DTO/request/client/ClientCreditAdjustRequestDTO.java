package com.example.backend.application.DTO.request.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreditAdjustRequestDTO {

    @NotNull
    @Pattern(regexp = "(?i)increase|decrease", message = "operation deve ser increase ou decrease")
    private String operation;

    @NotNull
    @Positive
    private BigDecimal amount;
}
