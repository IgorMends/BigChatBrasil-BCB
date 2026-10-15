package com.example.backend.application.DTO.response.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageResponseDTO {

    private UUID id;
    private String status;
    private LocalDateTime estimatedDelivery;
    private BigDecimal cost;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal currentBalance;
}