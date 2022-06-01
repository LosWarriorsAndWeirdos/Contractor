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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/contractors")
public class ContractorController {
    @Autowired
    private ContractorService contractorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contractor>> fetchAll() {
        try {
            List<Contractor> contractors = contractorService.findAll();
            return new ResponseEntity<List<Contractor>>(contractors, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contractor> fetchById(@PathVariable("id") Long id) {
        try {
            Optional<Contractor> optionalContractor = contractorService.findById(id);
            if (optionalContractor.isPresent()) {
                return new ResponseEntity<Contractor>(optionalContractor.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/contractor/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contractor> fetchByDni(@PathVariable("dni") String dni) {
        try {
            Optional<Contractor> optionalContractor = contractorService.findByDni(dni);
            if (optionalContractor.isPresent()) {
                return ResponseEntity.ok(optionalContractor.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Contractor> createContractor(@RequestBody Contractor contractor, BindingResult result) throws Exception {
       if (result.hasErrors()) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
       }
       Contractor contractorDB = contractorService.create(contractor);
       return ResponseEntity.status(HttpStatus.CREATED).body(contractorDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateContractor(@PathVariable("id") long id, @RequestBody Contractor contractor) throws Exception {
        Contractor contractorDB = contractorService.getContractor(id);

        if (contractorDB == null) {
            return ResponseEntity.notFound().build();
        }

        contractorDB = contractorService.update(contractor);
        return ResponseEntity.ok(contractorDB);
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
