package com.example.backend.application.controllers;

import com.example.backend.application.DTO.response.queue.QueueStatusResponseDTO;
import com.example.backend.application.security.AdminOnly;
import com.example.backend.domain.usecase.queue.QueueGetStatusUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AdminOnly
@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueGetStatusUsecase getStatusUsecase;

    @GetMapping("/status")
    public ResponseEntity<QueueStatusResponseDTO> status() {
        return ResponseEntity.ok(getStatusUsecase.execute());
    }
}
