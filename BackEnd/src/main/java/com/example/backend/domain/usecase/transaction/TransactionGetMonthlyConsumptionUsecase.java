package com.example.backend.domain.usecase.transaction;

import com.example.backend.domain.entity.enums.TransactionType;
import com.example.backend.domain.port.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionGetMonthlyConsumptionUsecase {

    private final TransactionRepository transactionRepository;


    public BigDecimal execute(UUID clientId) {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        BigDecimal debits = transactionRepository
                .sumAmountByClientIdAndTypeSince(clientId, TransactionType.DEBIT, startOfMonth);
        BigDecimal credits = transactionRepository
                .sumAmountByClientIdAndTypeSince(clientId, TransactionType.CREDIT, startOfMonth);

        return debits.subtract(credits).max(BigDecimal.ZERO);
    }
}
