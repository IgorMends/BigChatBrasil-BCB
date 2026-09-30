package com.example.backend.domain.usecases.client;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientSaveUsecase {

    private final ClientRepository clientRepository;

    public ClientSaveUsecase(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public void save(ClientEntity client){
        try {
            clientRepository.save(client);
        }catch (Exception e){
            return;
        }
    }
}
