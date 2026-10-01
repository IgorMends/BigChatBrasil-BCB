package com.example.backend.application.DTO.request;

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
public class ClientRequestDTO {

    private String name;
    private String document;
    private String documentType;
    private String planType;
    private float balance;
    private float creditLimit;
    private boolean active;
}