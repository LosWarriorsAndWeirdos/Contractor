package com.example.contractorservice.controllers;

import com.example.contractorservice.entity.Contractor;
import com.example.contractorservice.services.ContractorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/contractors")
public class ContractorController {
    @Autowired
    private ContractorService contractorService;

    @GetMapping()
    public ResponseEntity<List<Contractor>> fetchAll() {
        List<Contractor> contractors = new ArrayList<>();
        contractors = contractorService.findAllContractors();

        if (contractors.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contractors);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Contractor> fetchById(@PathVariable("id") Long id) {
        log.info("Getting Contractor ID {}", id);
        Contractor contractor = contractorService.getContractor(id);
        if (contractor == null) {
            log.error("This Contractor ID {} doens't found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contractor);
    }

    @GetMapping(path = "/contractor/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contractor> fetchByDni(@PathVariable("dni") String dni) {
        log.info("Getting Contractor by DNI {}", dni);
        Contractor contractor = contractorService.findByDni(dni);
        if  (contractor == null){
            log.error("This Contractor with DNI {} doesn't found");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contractor);
    }

    @PostMapping
    public ResponseEntity<Contractor> createContractor(@RequestBody Contractor contractor, BindingResult result) throws Exception {
       log.info("Creating Contractor {}", contractor);
       if (result.hasErrors()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
       }

       Contractor contractorDB = contractorService.createContractor(contractor);
       return ResponseEntity.status(HttpStatus.CREATED).body(contractorDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateContractor(@PathVariable("id") long id, @RequestBody Contractor contractor) throws Exception {
       log.info("Updating Contractor ID {}", id);
       Contractor currentContractor = contractorService.getContractor(id);

       if (currentContractor == null) {
           log.error("It can't be updated. Contractor ID {} doesn't exists", id);
           return ResponseEntity.notFound().build();
       }
       contractor.setId(id);
       currentContractor = contractorService.updateContractor(contractor);
       return ResponseEntity.ok(currentContractor);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Contractor> deleteContractor(@PathVariable("id") long id){
        log.info("Deleting Contractor ID {}");
        Contractor contractor = contractorService.getContractor(id);
        if (contractor == null){
            log.error("Can't be deleted Contractor ID {} because it doesn't found");
            return ResponseEntity.notFound().build();
        }
        contractor = contractorService.deleteContractor(contractor);
        return ResponseEntity.ok(contractor);
    }
    private String formatMessage(BindingResult result)
    {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
