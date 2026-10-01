package com.example.backend.domain.port;

import com.example.backend.domain.entity.ClientEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {

    ClientEntity save(ClientEntity client);
    Optional<ClientEntity> findById(UUID id);
    List<ClientEntity> findAll();
}
