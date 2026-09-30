package com.example.backend.application.controllers;

import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.usecases.client.ClientSaveUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientSaveUsecase clientSaveUsecase;

    //Response Entity deve retornar o DTO
    @PostMapping("/save")
    public ResponseEntity<Void> saveClient(@RequestBody ClientEntity client){

        clientSaveUsecase.save(client);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
