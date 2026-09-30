package com.example.backend.infrastructure.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
    @Column("planType")
    private String planType;
    @Column("balance")
    private float balance;
    @Column("limit")
    private float limit;
    @Column("active")
    private boolean active;
}
