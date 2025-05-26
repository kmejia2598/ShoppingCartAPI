package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.login.LoginRequest;
import org.shoppingcart.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientService clientService;

    /**
     * Log in with the default username, obtained from the API https://fakestoreapi.com/
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(clientService.login(request));
    }
}
