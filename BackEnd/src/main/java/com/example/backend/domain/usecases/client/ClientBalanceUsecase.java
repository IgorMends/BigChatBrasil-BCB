package com.example.backend.domain.usecases.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.response.ClientResponseDTO;
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

    public ClientResponseDTO getBalance(UUID id) {

        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        //Melhorar DTO para retornar apenas o balance
        return ClientApiMapper.toSaveResponseDTO(entity);
    }
}