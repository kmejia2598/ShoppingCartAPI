package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.OrderDTO;
import org.shoppingcart.dto.PaymentRequestDTO;
import org.shoppingcart.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/add")
    public ResponseEntity<?> addPaymentMemory(@RequestBody PaymentRequestDTO payment) {
        return ResponseEntity.ok(paymentService.addPaymetMemory(payment));
    }
}
