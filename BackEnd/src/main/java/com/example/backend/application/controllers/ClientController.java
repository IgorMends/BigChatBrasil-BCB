package com.example.backend.application.controllers;

import com.example.backend.application.DTO.request.ClientRequestDTO;
import com.example.backend.application.DTO.response.ClientResponseDTO;
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

    @PostMapping("/save")
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody ClientRequestDTO client){


        ClientResponseDTO response = clientSaveUsecase.save(client);

        return ResponseEntity.ok(response);
    }
}
