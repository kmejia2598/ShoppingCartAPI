package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.payment.PaymentRequestDTO;
import org.shoppingcart.service.PaymentService;
import org.shoppingcart.utils.GeneralMethos;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class PaymentController {

    private final PaymentService paymentService;
    private final GeneralMethos gM;

    /**
     * Endpoint to simulate the order payment process
     */
    @PostMapping("/add")
    public ResponseEntity<?> addPaymentMemory(@Validated  @RequestBody PaymentRequestDTO payment, BindingResult bindingResult) {
        ResponseEntity<?> errores = gM.validarErrores(bindingResult);
        if (errores != null) return errores;

        return ResponseEntity.ok(paymentService.addPaymetMemory(payment));
    }
}
