package com.example.contractorservice.services;

import com.example.contractorservice.entity.Contractor;

import java.util.List;
import java.util.Optional;

public interface ContractorService{
    public Contractor findByDni(String dni);
    public List<Contractor> findAllContractors();
    public Contractor createContractor(Contractor contractor);
    public Contractor updateContractor(Contractor contractor);
    public Contractor deleteContractor(Contractor contractor);
    public Contractor getContractor(Long id);
}
