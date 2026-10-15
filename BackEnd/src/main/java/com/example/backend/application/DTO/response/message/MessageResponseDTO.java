package com.example.backend.application.DTO.response.message;

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
public class MessageResponseDTO {

    private UUID id;
    private UUID conversationId;
    private String senderId;
    private String senderType;
    private String recipientId;
    private String content;
    private LocalDateTime timestamp;
    private String priority;
    private String status;
    private BigDecimal cost;
}