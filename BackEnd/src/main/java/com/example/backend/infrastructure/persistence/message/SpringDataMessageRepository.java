package com.example.backend.infrastructure.persistence.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataMessageRepository extends CrudRepository<MessageDocument, UUID> {

    @Query("""
            SELECT m.* FROM message m
            JOIN conversation c ON c.id = m.conversation_id
            WHERE m.id = :id AND c.client_id = :clientId
            """)
    Optional<MessageDocument> findByIdAndClientId(@Param("id") UUID id, @Param("clientId") UUID clientId);

    @Query("""
            SELECT m.* FROM message m
            JOIN conversation c ON c.id = m.conversation_id
            WHERE m.conversation_id = :conversationId AND c.client_id = :clientId
            ORDER BY m.created_at ASC
            LIMIT :limit OFFSET :offset
            """)
    List<MessageDocument> findByConversationIdAndClientId(@Param("conversationId") UUID conversationId,
                                                        @Param("clientId") UUID clientId,
                                                        @Param("limit") int limit,
                                                        @Param("offset") int offset);

    // CAST evita o erro "could not determine data type of parameter" do Postgres quando o filtro é nulo
    @Query("""
            SELECT m.* FROM message m
            JOIN conversation c ON c.id = m.conversation_id
            WHERE c.client_id = :clientId
              AND (CAST(:status AS VARCHAR) IS NULL OR m.status = CAST(:status AS VARCHAR))
              AND (CAST(:priority AS VARCHAR) IS NULL OR m.priority = CAST(:priority AS VARCHAR))
              AND (CAST(:conversationId AS UUID) IS NULL OR m.conversation_id = CAST(:conversationId AS UUID))
              AND (CAST(:from AS TIMESTAMP) IS NULL OR m.created_at >= CAST(:from AS TIMESTAMP))
              AND (CAST(:to AS TIMESTAMP) IS NULL OR m.created_at <= CAST(:to AS TIMESTAMP))
            ORDER BY m.created_at DESC
            LIMIT :limit OFFSET :offset
            """)
    List<MessageDocument> findAllByClientId(@Param("clientId") UUID clientId,
                                          @Param("status") String status,
                                          @Param("priority") String priority,
                                          @Param("conversationId") UUID conversationId,
                                          @Param("from") LocalDateTime from,
                                          @Param("to") LocalDateTime to,
                                          @Param("limit") int limit,
                                          @Param("offset") int offset);

    @Modifying
    @Query("UPDATE message SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);

    @Query("SELECT * FROM message WHERE status = :status ORDER BY created_at ASC")
    List<MessageDocument> findByStatus(@Param("status") String status);

    @Query("SELECT COUNT(*) FROM message WHERE status = :status")
    long countByStatus(@Param("status") String status);
}