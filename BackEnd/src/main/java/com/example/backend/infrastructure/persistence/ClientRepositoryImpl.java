package com.example.backend.infrastructure.persistence;

import com.example.backend.infrastructure.persistence.infraMappers.ClientMapper;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final SpringDataClientRepository springDataClientRepository;

    public ClientRepositoryImpl(SpringDataClientRepository springDataClientRepository) {
        this.springDataClientRepository =  springDataClientRepository;
    }

    @Override
    public ClientEntity save(ClientEntity client) {
        ClientDocument doc = ClientMapper.toDocument(client);   // id = null -> INSERT
        ClientDocument saved = springDataClientRepository.save(doc);
        return ClientMapper.toDomain(saved);
    }

    @Override
    public Optional<ClientEntity> findById(UUID id) {
        return springDataClientRepository.findById(id).map(ClientMapper::toDomain);
    }

    @Override
    public List<ClientEntity> findAll() {
        List<ClientEntity> clients = new java.util.ArrayList<>();
        springDataClientRepository.findAll().forEach(doc -> clients.add(ClientMapper.toDomain(doc)));
        return clients;
    }
}
