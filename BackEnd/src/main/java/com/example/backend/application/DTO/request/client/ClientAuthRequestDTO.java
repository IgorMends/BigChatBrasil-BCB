package com.example.backend.application.DTO.request.client;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthRequestDTO {

    @NotBlank
    private String document;

    @NotBlank
    private String documentType;
}
