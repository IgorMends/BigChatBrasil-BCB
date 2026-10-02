package com.example.backend.domain.usecases.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.response.client.ClientBalanceResponseDTO;
import com.example.backend.application.DTO.response.client.ClientResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.exeptions.ClientNotFoundException;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientBalanceUsecase {

    private final ClientRepository clientRepository;

    public ClientBalanceUsecase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientBalanceResponseDTO getBalance(UUID id) {

        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return ClientApiMapper.toBalanceResponseDTO(entity);
    }
}