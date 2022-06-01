package com.example.contractorservice.services.impl;

import com.example.contractorservice.entity.Contractor;
import com.example.contractorservice.repository.ContractorRepository;
import com.example.contractorservice.services.ContractorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContractorServiceImpl implements ContractorService {

    private ContractorRepository contractorRepository;

    @Override
    @Transactional
    public Contractor save(Contractor entity) throws Exception {
        return contractorRepository.save(entity);
    }

    @Override
    @Transactional
    public Contractor update(Contractor entity) throws Exception {
        return contractorRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {
        contractorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Contractor create(Contractor entity) throws Exception {
        return contractorRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contractor> findByDni(String dni) throws Exception {
        return contractorRepository.findByDni(dni);
    }

    @Override
    public Contractor getContractor(Long id) throws Exception {
        return contractorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contractor> findAll() throws Exception {
        return contractorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contractor> findById(Long id) throws Exception {
        return contractorRepository.findById(id);
    }
}
