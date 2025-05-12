package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Clients")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientRepository ClientRepository;

    public ClientController(ClientRepository ClientRepository) {
        this.ClientRepository = ClientRepository;
    }

    // POST - Add a new Client
    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client savedClient = ClientRepository.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    // GET - Fetch all Clients (optional, useful for admin panel or testing)
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = ClientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
}
