package com.example.backend.application.DTO.request.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ClientSaveRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String document;
    @NotBlank
    private String documentType;
    @NotBlank
    private String planType;
    @NotNull
    @PositiveOrZero
    private float balance;
    @NotNull
    @PositiveOrZero
    private float creditLimit;
    @NotNull
    private boolean active;
}