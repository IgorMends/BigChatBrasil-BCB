package com.example.backend.application.DTO.response.client;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientAuthResponseDTO {

    private UUID id;
    private String name;
    private String documentType;
    private String planType;
}
