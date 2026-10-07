package com.example.backend.domain.port;

import com.example.backend.domain.entity.ConversationEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository {

    ConversationEntity save(ConversationEntity conversation);

    Optional<ConversationEntity> findById(UUID id);

    Optional<ConversationEntity> findByIdAndClientId(UUID id, UUID clientId);

    Optional<ConversationEntity> findByClientIdAndRecipientId(UUID clientId, String recipientId);

    List<ConversationEntity> findAllByClientId(UUID clientId);

    List<ConversationEntity> searchByClientIdAndRecipientName(UUID clientId, String search);
}