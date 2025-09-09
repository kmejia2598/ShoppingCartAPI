package org.shoppingcart.dto.client;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreditCardDetailsDTO {

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{13,19}", message = "Card number must be between 13 and 19 digits")
    private String cardNumber;

    @NotBlank(message = "Expiry date is required")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiry date must be in MM/YY format")
    private String expiryDate;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @NotBlank(message = "Card holder name is required")
    @Size(max = 100, message = "Card holder name must not exceed 100 characters")
    private String cardHolderName;
}
