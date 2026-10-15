package com.example.backend.domain.port;

import com.example.backend.domain.entity.TransactionEntity;
import com.example.backend.domain.entity.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionRepository {

    TransactionEntity save(TransactionEntity transaction);

    BigDecimal sumAmountByClientIdAndTypeSince(UUID clientId, TransactionType type, LocalDateTime since);
}
