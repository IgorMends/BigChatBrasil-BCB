package com.example.backend.domain.entity;

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
public class ConversationEntity {

    private UUID id;
    private UUID clientId;
    private String recipientId;
    private String recipientName;
    private String recipientPhoneNumber;
    private String lastMessageContent;
    private LocalDateTime lastMessageTime;
    private Integer unreadCount;

    public void markAsRead() {
        this.unreadCount = 0;
    }

    public void incrementUnreadCount() {
        this.unreadCount = (this.unreadCount == null ? 0 : this.unreadCount) + 1;
    }

    public void registerLastMessage(String content, LocalDateTime time) {
        this.lastMessageContent = content;
        this.lastMessageTime = time;
    }
}