package com.example.backend.domain.usecases.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.response.ClientResponseDTO;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientListUsecase {

    private final ClientRepository clientRepository;

    public ClientListUsecase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientResponseDTO> findAll() {

        return clientRepository.findAll()
                .stream()
                .map(ClientApiMapper::toSaveResponseDTO)
                .toList();
    }
}