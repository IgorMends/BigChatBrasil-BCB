package com.example.backend.domain.usecase.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.request.client.ClientSaveRequestDTO;
import com.example.backend.application.DTO.response.client.ClientResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientSaveUsecase {

    private final ClientRepository clientRepository;

    public ClientSaveUsecase(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO save(ClientSaveRequestDTO client){

        ClientEntity entity = ClientApiMapper.toDomain(client);

        ClientResponseDTO response = ClientApiMapper.toSaveResponseDTO(clientRepository.save(entity));

        return response;
    }
}
