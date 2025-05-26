package org.shoppingcart.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private int id;
    private Integer orderId;
    private String paymentMethod;
    private String currency;
    private String paypalEmail;
    private CreditCardDetailsDTO cardDetails;
}
