package com.example.backend.domain.usecase.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.response.client.ClientFindResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientFindByIdUsecase {

    private final ClientRepository clientRepository;

    public ClientFindByIdUsecase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientFindResponseDTO findById(UUID id) {

        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return ClientApiMapper.toFindResponseDTO(entity);
    }
}