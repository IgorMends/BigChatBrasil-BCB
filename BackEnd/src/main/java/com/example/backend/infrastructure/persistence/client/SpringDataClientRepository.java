package com.example.backend.infrastructure.persistence.client;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataClientRepository extends CrudRepository<ClientDocument, UUID>{


    @Query("SELECT * FROM client WHERE id = :id FOR UPDATE")
    Optional<ClientDocument> findByIdForUpdate(@Param("id") UUID id);

    Optional<ClientDocument> findByDocument(String document);
}
