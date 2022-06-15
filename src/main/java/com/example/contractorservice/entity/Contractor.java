package com.example.contractorservice.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "contractors")
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "This field can't be empty")
    @Size(min = 9, max = 9, message = "It's longer")
    @Column(name = "dni", length = 9,nullable = false)
    private String dni;

    @NotEmpty(message = "This field can't be empty")
    @Size(min = 5, max = 50, message = "It's longer")
    @Column(name = "description", length = 50, nullable = false)
    private String description;
}
