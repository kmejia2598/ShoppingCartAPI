package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/client")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ClientController {

    private final ClientService clientService;

    /**
     * Get the list of API clients https://fakestoreapi.com/
     */
    @GetMapping("/all")
    public ResponseEntity<?> productList() {
        return ResponseEntity.ok(clientService.getClients());
    }

    /**
     * Get the list of Memory clients
     */
    @GetMapping("/all/in-memory")
    public ResponseEntity<?> productMemory() {
        return ResponseEntity.ok(clientService.getAllClientsMemory());
    }

    /**
     * Get the client of API
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> productById(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    /**
     * Get the client of Memory
     */
    @GetMapping("/get/in-memory/{id}")
    public ResponseEntity<?> productByIdMemory(@PathVariable Integer id) {
        return ResponseEntity.ok(clientService.getClientByIdMemory(id));
    }
}
