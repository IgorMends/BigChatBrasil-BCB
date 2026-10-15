package com.example.backend.domain.entity;

import com.example.backend.domain.entity.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    private UUID id;
    private UUID clientId;
    private UUID messageId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}