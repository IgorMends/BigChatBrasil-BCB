package com.example.backend.infrastructure.persistence.conversation;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataConversationRepository extends CrudRepository<ConversationDocument, UUID> {

    Optional<ConversationDocument> findByIdAndClientId(UUID id, UUID clientId);

    Optional<ConversationDocument> findByClientIdAndRecipientId(UUID clientId, String recipientId);

    List<ConversationDocument> findByClientIdOrderByLastMessageTimeDesc(UUID clientId);

    List<ConversationDocument> findByClientIdAndRecipientNameContainingIgnoreCaseOrderByLastMessageTimeDesc(
            UUID clientId, String recipientName);
}