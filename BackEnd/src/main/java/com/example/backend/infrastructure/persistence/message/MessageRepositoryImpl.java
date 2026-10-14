package com.example.backend.infrastructure.persistence.message;

import com.example.backend.domain.entity.MessageEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.entity.enums.MessageStatus;
import com.example.backend.domain.port.MessageRepository;
import com.example.backend.infrastructure.persistence.infraMappers.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

    private final SpringDataMessageRepository springDataMessageRepository;

    @Override
    public MessageEntity save(MessageEntity message) {
        return MessageMapper.toDomain(springDataMessageRepository.save(MessageMapper.toDocument(message)));
    }

    @Override
    public Optional<MessageEntity> findByIdAndClientId(UUID id, UUID clientId) {
        return springDataMessageRepository.findByIdAndClientId(id, clientId).map(MessageMapper::toDomain);
    }

    @Override
    public Optional<MessageEntity> findById(UUID id) {
        return springDataMessageRepository.findById(id).map(MessageMapper::toDomain);
    }

    @Override
    public List<MessageEntity> findByConversationIdAndClientId(UUID conversationId, UUID clientId, int page, int size) {
        return springDataMessageRepository.findByConversationIdAndClientId(conversationId, clientId, size, page * size)
                .stream().map(MessageMapper::toDomain).toList();
    }

    @Override
    public List<MessageEntity> findAllByClientId(UUID clientId, MessageStatus status, MessagePriority priority,
                                                 UUID conversationId, LocalDateTime from, LocalDateTime to,
                                                 int page, int size) {
        return springDataMessageRepository.findAllByClientId(clientId, MessageMapper.toDb(status), MessageMapper.toDb(priority),
                        conversationId, from, to, size, page * size)
                .stream().map(MessageMapper::toDomain).toList();
    }

    @Override
    public void updateStatus(UUID id, MessageStatus status) {
        springDataMessageRepository.updateStatus(id, MessageMapper.toDb(status));
    }

    @Override
    public List<MessageEntity> findByStatus(MessageStatus status) {
        return springDataMessageRepository.findByStatus(MessageMapper.toDb(status))
                .stream().map(MessageMapper::toDomain).toList();
    }

    @Override
    public long countByStatus(MessageStatus status) {
        return springDataMessageRepository.countByStatus(MessageMapper.toDb(status));
    }
}