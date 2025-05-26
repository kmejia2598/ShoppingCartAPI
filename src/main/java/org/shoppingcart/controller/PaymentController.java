package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.PaymentRequestDTO;
import org.shoppingcart.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Endpoint to simulate the order payment process
     */
    @PostMapping("/add")
    public ResponseEntity<?> addPaymentMemory(@RequestBody PaymentRequestDTO payment) {
        return ResponseEntity.ok(paymentService.addPaymetMemory(payment));
    }
}
