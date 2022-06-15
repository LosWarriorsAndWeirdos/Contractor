package com.example.contractorservice.repository;
import com.example.contractorservice.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    Contractor findByDni(String dni);

}

