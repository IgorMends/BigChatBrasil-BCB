package com.example.backend.infrastructure.persistence.message;

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

@Table("message")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDocument {

    @Id
    private UUID id;
    @Column("conversation_id")
    private UUID conversationId;
    @Column("sender_id")
    private String senderId;
    @Column("sender_type")
    private String senderType;
    @Column("recipient_id")
    private String recipientId;
    @Column("content")
    private String content;
    @Column("priority")
    private String priority;
    @Column("status")
    private String status;
    @Column("cost")
    private BigDecimal cost;
    @Column("created_at")
    private LocalDateTime createdAt;
}