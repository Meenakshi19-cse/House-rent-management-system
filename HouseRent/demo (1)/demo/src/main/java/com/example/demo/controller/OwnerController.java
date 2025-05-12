package com.example.demo.controller;

import com.example.demo.model.Owner;
import com.example.demo.repository.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin(origins = "*") // You can restrict this to your frontend domain for better security
public class OwnerController {

    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // POST - Add new owner
    @PostMapping
    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }

    // GET - Fetch all owners
    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

}
