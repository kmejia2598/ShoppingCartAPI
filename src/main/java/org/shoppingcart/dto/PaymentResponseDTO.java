package org.shoppingcart.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponseDTO {
    private String transactionId;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
