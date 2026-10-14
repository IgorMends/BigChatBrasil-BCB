package com.example.backend.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.entity.enums.MessageStatus;
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
public class MessageEntity {

    private UUID id;
    private UUID conversationId;
    private String senderId;
    private String senderType;
    private String recipientId;
    private String content;
    private MessagePriority priority;
    private MessageStatus status;
    private BigDecimal cost;
    private LocalDateTime createdAt;
}