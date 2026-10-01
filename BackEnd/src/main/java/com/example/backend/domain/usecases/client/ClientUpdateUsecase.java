package com.example.backend.domain.usecases.client;

import com.example.backend.application.DTO.apiMappers.ClientApiMapper;
import com.example.backend.application.DTO.request.ClientRequestDTO;
import com.example.backend.application.DTO.response.ClientResponseDTO;
import com.example.backend.domain.entity.ClientEntity;
import com.example.backend.domain.exeptions.ClientNotFoundException;
import com.example.backend.domain.port.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientUpdateUsecase {

    private final ClientRepository clientRepository;

    public ClientUpdateUsecase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDTO update(UUID id, ClientRequestDTO dadosAtualizados) {

        ClientEntity existente = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        existente.setName(dadosAtualizados.getName());
        existente.setDocument(dadosAtualizados.getDocument());
        existente.setDocumentType(dadosAtualizados.getDocumentType());
        existente.setPlanType(dadosAtualizados.getPlanType());
        existente.setBalance(dadosAtualizados.getBalance());
        existente.setCreditLimit(dadosAtualizados.getCreditLimit());
        existente.setActive(dadosAtualizados.isActive());

        ClientEntity salvo = clientRepository.save(existente);

        return ClientApiMapper.toSaveResponseDTO(salvo);
    }
}