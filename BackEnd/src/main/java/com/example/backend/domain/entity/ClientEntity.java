package com.example.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEntity {

    private UUID id;
    private String name;
    private String document;
    private String documentType;
    private String planType;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private boolean active;

    public void deactivate() {
        this.active = false;
    }
}
