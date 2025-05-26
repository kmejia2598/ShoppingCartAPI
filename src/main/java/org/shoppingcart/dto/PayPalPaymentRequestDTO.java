package org.shoppingcart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PayPalPaymentRequestDTO {
    private String email;
    private BigDecimal amount;
    private String currency;
    private String description;
}
