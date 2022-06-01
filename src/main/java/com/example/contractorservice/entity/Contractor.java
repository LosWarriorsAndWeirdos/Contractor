package com.example.contractorservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "contractors")
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10,nullable = false)
    private String dni;

    @Column(length = 50, nullable = false)
    private String description;
}
