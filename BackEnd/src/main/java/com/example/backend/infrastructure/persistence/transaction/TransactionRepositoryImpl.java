package com.example.backend.infrastructure.persistence.transaction;

import com.example.backend.domain.entity.TransactionEntity;
import com.example.backend.domain.entity.enums.TransactionType;
import com.example.backend.domain.port.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.backend.infrastructure.persistence.infraMappers.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final SpringDataTransactionRepository springData;

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        return TransactionMapper.toDomain(springData.save(TransactionMapper.toDocument(transaction)));
    }

    @Override
    public BigDecimal sumAmountByClientIdAndTypeSince(UUID clientId, TransactionType type, LocalDateTime since) {
        return springData.sumAmountByClientIdAndTypeSince(clientId, TransactionMapper.toDb(type), since);
    }
}