package com.example.backend.domain.port;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.entity.enums.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    MessageEntity save(MessageEntity message);

    Optional<MessageEntity> findByIdAndClientId(UUID id, UUID clientId);

    Optional<MessageEntity> findById(UUID id);

    List<MessageEntity> findByConversationIdAndClientId(UUID conversationId, UUID clientId, int page, int size);

    List<MessageEntity> findAllByClientId(UUID clientId,
                                    MessageStatus status,
                                    MessagePriority priority,
                                    UUID conversationId,
                                    LocalDateTime from,
                                    LocalDateTime to,
                                    int page,
                                    int size);

    void updateStatus(UUID id, MessageStatus status);

    List<MessageEntity> findByStatus(MessageStatus status);

    long countByStatus(MessageStatus status);
}