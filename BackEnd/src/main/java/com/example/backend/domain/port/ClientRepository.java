package com.example.backend.domain.port;

import com.example.backend.domain.entity.ClientEntity;

import java.util.Optional;

public interface ClientRepository {

    ClientEntity save(ClientEntity client);
    Optional<ClientEntity> findById(String id);
}
