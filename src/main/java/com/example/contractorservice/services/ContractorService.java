package com.example.contractorservice.services;

import com.example.contractorservice.entity.Contractor;

import java.util.Optional;

public interface ContractorService extends CrudService<Contractor, Long> {
    Optional<Contractor> findByDni(String dni) throws Exception;
    Contractor getContractor(Long id) throws Exception;
}
