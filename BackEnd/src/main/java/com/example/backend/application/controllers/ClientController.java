package com.example.backend.application.controllers;

import com.example.backend.application.DTO.request.client.ClientCreditAdjustRequestDTO;
import com.example.backend.application.DTO.request.client.ClientSaveRequestDTO;
import com.example.backend.application.DTO.response.client.ClientBalanceResponseDTO;
import com.example.backend.application.DTO.response.client.ClientCreditAdjustResponseDTO;
import com.example.backend.application.DTO.response.client.ClientFindResponseDTO;
import com.example.backend.application.DTO.response.client.ClientResponseDTO;
import com.example.backend.application.security.AdminOnly;
import com.example.backend.application.security.CurrentClient;
import com.example.backend.domain.exception.ClientNotFoundException;
import com.example.backend.domain.usecase.client.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientSaveUsecase clientSaveUsecase;
    private final ClientFindByIdUsecase clientFindByIdUsecase;
    private final ClientBalanceUsecase clientBalanceUsecase;
    private final ClientListUsecase clientListUsecase;
    private final ClientUpdateUsecase clientUpdateUsecase;
    private final ClientDeactivateUsecase clientDeactivateUsecase;
    private final ClientAdjustCreditUsecase adjustCreditUsecase;

    @AdminOnly
    @PostMapping("/save")
    public ResponseEntity<ClientResponseDTO> saveClient(@Valid @RequestBody ClientSaveRequestDTO client){

        ClientResponseDTO response = clientSaveUsecase.save(client);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientFindResponseDTO> findClient(@PathVariable UUID id){

        ClientFindResponseDTO response = clientFindByIdUsecase.findById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<ClientBalanceResponseDTO> getBalance(@CurrentClient UUID clientId, @PathVariable UUID id){

        if (!id.equals(clientId)) {
            throw new ClientNotFoundException(id);
        }

        ClientBalanceResponseDTO response = clientBalanceUsecase.getBalance(id);

        return ResponseEntity.ok(response);
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<ClientResponseDTO>> getAll(){

        List<ClientResponseDTO> response = clientListUsecase.findAll();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ClientSaveRequestDTO dto) {

        ClientResponseDTO response = clientUpdateUsecase.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Map<String, String>> deactivate(@PathVariable UUID id) {

        clientDeactivateUsecase.deactivate(id);

        return ResponseEntity.ok(Map.of("message", "Cliente inativado com sucesso"));
    }

    @AdminOnly
    @PostMapping("/{id}/credits")
    public ResponseEntity<ClientCreditAdjustResponseDTO> adjustCredit(
            @PathVariable UUID id,
            @RequestBody @Valid ClientCreditAdjustRequestDTO request) {
        return ResponseEntity.ok(adjustCreditUsecase.execute(id, request));
    }
}
