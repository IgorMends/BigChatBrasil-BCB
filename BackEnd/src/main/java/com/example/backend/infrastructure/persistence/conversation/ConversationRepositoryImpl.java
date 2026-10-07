package com.example.backend.infrastructure.persistence.conversation;

import com.example.backend.domain.entity.ConversationEntity;
import com.example.backend.domain.port.ConversationRepository;
import com.example.backend.infrastructure.persistence.infraMappers.ConversationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ConversationRepositoryImpl implements ConversationRepository {

    private final SpringDataConversationRepository springDataConversationRepository;

    public ConversationRepositoryImpl(SpringDataConversationRepository springDataConversationRepository) {
        this.springDataConversationRepository = springDataConversationRepository;
    }

    @Override
    public ConversationEntity save(ConversationEntity conversation) {
        ConversationDocument doc = ConversationMapper.toDocument(conversation);
        ConversationDocument saved = springDataConversationRepository.save(doc);
        return ConversationMapper.toDomain(saved);
    }

    @Override
    public Optional<ConversationEntity> findById(UUID id) {
        return springDataConversationRepository.findById(id).map(ConversationMapper::toDomain);
    }

    @Override
    public Optional<ConversationEntity> findByIdAndClientId(UUID id, UUID clientId) {
        return springDataConversationRepository.findByIdAndClientId(id, clientId)
                .map(ConversationMapper::toDomain);
    }

    @Override
    public Optional<ConversationEntity> findByClientIdAndRecipientId(UUID clientId, String recipientId) {
        return springDataConversationRepository.findByClientIdAndRecipientId(clientId, recipientId)
                .map(ConversationMapper::toDomain);
    }

    @Override
    public List<ConversationEntity> findAllByClientId(UUID clientId) {
        return springDataConversationRepository.findByClientIdOrderByLastMessageTimeDesc(clientId)
                .stream()
                .map(ConversationMapper::toDomain)
                .toList();
    }

    @Override
    public List<ConversationEntity> searchByClientIdAndRecipientName(UUID clientId, String search) {
        return springDataConversationRepository
                .findByClientIdAndRecipientNameContainingIgnoreCaseOrderByLastMessageTimeDesc(clientId, search)
                .stream()
                .map(ConversationMapper::toDomain)
                .toList();
    }
}