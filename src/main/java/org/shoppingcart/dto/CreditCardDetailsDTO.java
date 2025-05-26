package org.shoppingcart.dto;

import lombok.Data;

@Data
public class CreditCardDetailsDTO {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolderName;
}
