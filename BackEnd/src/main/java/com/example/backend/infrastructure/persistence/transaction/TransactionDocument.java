package com.example.backend.infrastructure.persistence.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDocument {

    @Id
    private UUID id;
    @Column("client_id")
    private UUID clientId;
    @Column("message_id")
    private UUID messageId;
    @Column("type")
    private String type;
    @Column("amount")
    private BigDecimal amount;
    @Column("created_at")
    private LocalDateTime createdAt;
}

