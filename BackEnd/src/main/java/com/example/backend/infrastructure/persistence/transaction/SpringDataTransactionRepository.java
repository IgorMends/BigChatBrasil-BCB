package com.example.backend.infrastructure.persistence.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpringDataTransactionRepository extends CrudRepository<TransactionDocument, UUID> {

    @Query("""
            SELECT COALESCE(SUM(amount), 0) FROM transaction
            WHERE client_id = :clientId AND type = :type AND created_at >= :since
            """)
    BigDecimal sumAmountByClientIdAndTypeSince(@Param("clientId") UUID clientId,
                                               @Param("type") String type,
                                               @Param("since") LocalDateTime since);
}

