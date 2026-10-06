package com.example.backend.infrastructure.persistence.conversation;

import java.util.UUID;

public interface SpringDataConversationRepository
        extends CrudRepository<ConversationDocument, UUID> {

    List<ConversationDocument> findByClientIdOrderByLastMessageTimeDesc(UUID clientId);

    Optional<ConversationDocument> findByIdAndClientId(UUID id, UUID clientId);
}
