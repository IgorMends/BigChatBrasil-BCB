package com.example.backend.domain.usecase.transaction;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.entity.TransactionEntity;
import com.example.backend.domain.entity.enums.TransactionType;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.port.ClientRepository;
import com.example.backend.domain.port.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionRefundClientUsecase {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void execute(UUID clientId, BigDecimal amount, UUID messageId) {
        ClientEntity client = clientRepository.findByIdForUpdate(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        boolean prepaid = "prepaid".equalsIgnoreCase(String.valueOf(client.getPlanType()));

        if (prepaid) {
            BigDecimal balance = client.getBalance() == null ? BigDecimal.ZERO : client.getBalance();
            client.setBalance(balance.add(amount));
            clientRepository.save(client);
        }

        transactionRepository.save(TransactionEntity.builder()
                .clientId(clientId)
                .messageId(messageId)
                .type(TransactionType.CREDIT)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
