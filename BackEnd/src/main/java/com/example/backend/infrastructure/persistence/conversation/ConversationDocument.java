package com.example.backend.infrastructure.persistence.conversation;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("conversation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDocument {

    @Id
    private UUID id;
    @Column("client_id")
    private UUID clientId;
    @Column("recipient_id")
    private String recipientId;
    @Column("recipient_name")
    private String recipientName;
    @Column("recipient_phone_number")
    private String recipientPhoneNumber;
    @Column("last_message_content")
    private String lastMessageContent;
    @Column("last_message_time")
    private LocalDateTime lastMessageTime;
    @Column("unread_count")
    private Integer unreadCount;
}

