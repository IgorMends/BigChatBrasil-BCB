package com.example.backend.infrastructure.persistence.client;

import  lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDocument {

    @Id
    private UUID id;
    @Column("name")
    private String name;
    @Column("document")
    private String document;
    @Column("document_type")
    private String documentType;
    @Column("plan_type")
    private String planType;
    @Column("balance")
    private BigDecimal balance;
    @Column("credit_limit")
    private BigDecimal creditLimit;
    @Column("active")
    private boolean active;
}
