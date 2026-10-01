package com.example.backend.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringDataClientRepository extends CrudRepository<ClientDocument, UUID>{

}
