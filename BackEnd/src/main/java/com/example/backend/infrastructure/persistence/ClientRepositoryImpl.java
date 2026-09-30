package com.example.backend.infrastructure.persistence;

import com.example.backend.application.DTO.ClientMapper;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final SpringDataClientRepository springDataClientRepository;

    public ClientRepositoryImpl(SpringDataClientRepository springDataClientRepository) {
        this.springDataClientRepository =  springDataClientRepository;
    }

    @Override
    public ClientEntity save(ClientEntity client){

        ClientDocument entity = ClientMapper.toDocument(client);
        ClientDocument saved = springDataClientRepository.save(entity);
        return ClientMapper.toDomain(saved);
    }

    @Override
    public Optional<ClientEntity> findById(String id){
        return springDataClientRepository.findById(id).map(ClientMapper::toDomain);
    }
}
