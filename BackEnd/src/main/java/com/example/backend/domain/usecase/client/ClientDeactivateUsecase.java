package com.example.backend.domain.usecase.client;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientDeactivateUsecase {

    private final ClientRepository clientRepository;

    public ClientDeactivateUsecase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void deactivate(UUID id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        client.deactivate();

        clientRepository.save(client);
    }
}