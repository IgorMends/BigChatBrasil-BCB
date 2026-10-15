package com.example.backend.domain.usecase.transaction;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.entity.TransactionEntity;
import com.example.backend.domain.entity.enums.TransactionType;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.exception.InsufficientBalanceException;
import com.example.backend.domain.port.ClientRepository;
import com.example.backend.domain.port.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionChargeClientUsecase {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;


    @Transactional
    public BigDecimal execute(UUID clientId, BigDecimal cost, UUID messageId) {
        ClientEntity client = clientRepository.findByIdForUpdate(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        LocalDateTime now = LocalDateTime.now();
        boolean prepaid = "prepaid".equalsIgnoreCase(String.valueOf(client.getPlanType()));

        BigDecimal currentBalance = null;

        if (prepaid) {
            BigDecimal balance = client.getBalance();
            if (balance.compareTo(cost) < 0) {
                throw new InsufficientBalanceException("Saldo insuficiente");
            }
            client.setBalance(balance.subtract(cost));
            clientRepository.save(client);
            currentBalance = client.getBalance();
        } else {
            LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            BigDecimal consumed = transactionRepository
                    .sumAmountByClientIdAndTypeSince(clientId, TransactionType.DEBIT, startOfMonth);
            if (consumed.add(cost).compareTo(client.getCreditLimit()) > 0) {
                throw new InsufficientBalanceException("Limite mensal excedido");
            }
        }

        transactionRepository.save(TransactionEntity.builder()
                .clientId(clientId)
                .messageId(messageId)
                .type(TransactionType.DEBIT)
                .amount(cost)
                .createdAt(now)
                .build());

        return currentBalance;
    }
}