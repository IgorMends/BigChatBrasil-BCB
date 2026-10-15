package com.example.backend.domain.usecase.client;


import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.request.client.ClientCreditAdjustRequestDTO;
import com.example.backend.application.DTO.response.client.ClientCreditAdjustResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.entity.TransactionEntity;
import com.example.backend.domain.entity.enums.TransactionType;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.exception.InsufficientBalanceException;
import com.example.backend.domain.port.ClientRepository;
import com.example.backend.domain.port.TransactionRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientAdjustCreditUsecase {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public ClientCreditAdjustResponseDTO execute(UUID clientId, ClientCreditAdjustRequestDTO request) {
        ClientEntity client = clientRepository.findByIdForUpdate(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        boolean increase = "increase".equalsIgnoreCase(request.getOperation());
        BigDecimal amount = request.getAmount().setScale(2, RoundingMode.HALF_UP);
        boolean prepaid = "prepaid".equalsIgnoreCase(String.valueOf(client.getPlanType()));

        if (prepaid) {
            BigDecimal current = client.getBalance() == null ? BigDecimal.ZERO : client.getBalance();
            BigDecimal updated = increase ? current.add(amount) : current.subtract(amount);

            if (updated.signum() < 0) {
                throw new InsufficientBalanceException("O valor a remover é maior que o saldo do cliente");
            }

            client.setBalance(updated);
            transactionRepository.save(TransactionEntity.builder()
                    .clientId(clientId)
                    .messageId(null)
                    .type(increase ? TransactionType.CREDIT : TransactionType.DEBIT)
                    .amount(amount)
                    .createdAt(LocalDateTime.now())
                    .build());
        } else {
            BigDecimal current = client.getCreditLimit() == null ? BigDecimal.ZERO : client.getCreditLimit();
            BigDecimal updated = increase ? current.add(amount) : current.subtract(amount);

            if (updated.signum() < 0) {
                throw new IllegalArgumentException("O limite não pode ficar negativo");
            }

            client.setCreditLimit(updated);
        }

        ClientEntity saved = clientRepository.save(client);

        return ClientApiMapper.toCreditAdjustResponseDTO(saved);
    }
}
