package com.example.backend.domain.usecase.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.response.client.ClientBalanceResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.port.ClientRepository;
import com.example.backend.domain.usecase.transaction.TransactionGetMonthlyConsumptionUsecase;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientBalanceUsecase {

    private final ClientRepository clientRepository;
    private final TransactionGetMonthlyConsumptionUsecase consumptionUsecase;

    public ClientBalanceResponseDTO execute(UUID clientId) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));

        boolean prepaid = "prepaid".equalsIgnoreCase(String.valueOf(client.getPlanType()));

        BigDecimal consumed = prepaid ? null : consumptionUsecase.execute(clientId);

        return ClientApiMapper.toBalanceResponseDTO(client, consumed);
    }
}