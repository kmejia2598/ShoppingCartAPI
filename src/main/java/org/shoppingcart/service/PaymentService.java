package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.dto.OrderDTO;
import org.shoppingcart.dto.PaymentRequestDTO;
import org.shoppingcart.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final MemoryDB memoryDB;

    public PaymentResponseDTO addPaymetMemory(PaymentRequestDTO payment){
        return memoryDB.addPayment(payment);
    }
}
