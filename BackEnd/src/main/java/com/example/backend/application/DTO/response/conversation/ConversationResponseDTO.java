package com.example.backend.application.DTO.response.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationResponseDTO {

    private UUID id;
    private String recipientId;
    private String recipientName;
    private String recipientPhoneNumber;
    private String lastMessageContent;
    private LocalDateTime lastMessageTime;
    private Integer unreadCount;
}
