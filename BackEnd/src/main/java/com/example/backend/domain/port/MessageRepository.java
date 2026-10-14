package com.example.backend.domain.port;

import com.example.backend.domain.entity.MessageEntity;
import com.example.backend.domain.entity.enums.MessagePriority;
import com.example.backend.domain.entity.enums.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    // SendMessage, ProcessNextMessage
    MessageEntity save(MessageEntity message);

    // GetMessage / GetMessageStatus: só devolve se a mensagem pertence ao cliente (join com conversation.client_id)
    Optional<MessageEntity> findByIdAndClientId(UUID id, UUID clientId);

    // Worker: não tem cliente autenticado
    Optional<MessageEntity> findById(UUID id);

    // GET /conversations/:id/messages: mais antiga para a mais nova
    List<MessageEntity> findByConversationIdAndClientId(UUID conversationId, UUID clientId, int page, int size);

    // GET /messages com filtros opcionais (qualquer filtro nulo é ignorado)
    List<MessageEntity> findAllByClientId(UUID clientId,
                                    MessageStatus status,
                                    MessagePriority priority,
                                    UUID conversationId,
                                    LocalDateTime from,
                                    LocalDateTime to,
                                    int page,
                                    int size);

    // ProcessNextMessage / UpdateMessageStatus
    void updateStatus(UUID id, MessageStatus status);

    // Recarregar a fila ao reiniciar (fila persistente) e reprocessar falhas
    List<MessageEntity> findByStatus(MessageStatus status);

    // GET /queue/status
    long countByStatus(MessageStatus status);
}