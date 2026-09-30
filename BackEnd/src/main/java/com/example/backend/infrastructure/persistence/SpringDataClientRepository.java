package com.example.backend.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

public interface SpringDataClientRepository extends CrudRepository<ClientDocument, String>{

}
