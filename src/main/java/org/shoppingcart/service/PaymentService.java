package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.dto.payment.PaymentRequestDTO;
import org.shoppingcart.dto.payment.PaymentResponseDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MemoryDB memoryDB;

    public PaymentResponseDTO addPaymetMemory(PaymentRequestDTO payment){
        return memoryDB.addPayment(payment);
    }
}
