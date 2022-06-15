package com.example.contractorservice.services.impl;

import com.example.contractorservice.entity.Contractor;
import com.example.contractorservice.repository.ContractorRepository;
import com.example.contractorservice.services.ContractorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContractorServiceImpl implements ContractorService {

    @Autowired
    ContractorRepository contractorRepository;


    @Override
    public Contractor findByDni(String dni) {
        return contractorRepository.findByDni(dni);
    }

    @Override
    public List<Contractor> findAllContractors() {
        return contractorRepository.findAll();
    }

    @Override
    public Contractor createContractor(Contractor contractor) {

        Contractor contractorDb = contractorRepository.findById((contractor.getId())).orElse(null);
        if (contractorDb != null){
            return contractorDb;
        }
        contractorDb = contractorRepository.save(contractor);
        return contractorDb;
    }

    @Override
    public Contractor updateContractor(Contractor contractor) {
        Contractor contractorDB = getContractor(contractor.getId());
        if (contractorDB == null) {
            return null;
        }
        contractorDB.setDni(contractor.getDni());
        contractorDB.setDescription(contractor.getDescription());

        return contractorRepository.save(contractorDB);
    }

    @Override
    public Contractor deleteContractor(Contractor contractor) {
        Contractor contractorDB = getContractor(contractor.getId());
        if (contractorDB == null) {
            return null;
        }

        return contractorRepository.save(contractor);
    }

    @Override
    public Contractor getContractor(Long id) {
        return contractorRepository.findById(id).orElse(null);
    }
}
