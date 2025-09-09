package org.shoppingcart.dto.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.shoppingcart.dto.client.CreditCardDetailsDTO;

@Data
public class PaymentRequestDTO {

    private int id;

    @NotNull(message = "The order ID is required")
    @Positive(message = "The order ID must be greater than 0")
    private Integer orderId;

    @NotBlank(message = "The payment method is required")
    private String paymentMethod;

    @NotBlank(message = "The currency is required")
    private String currency;

    @Email(message = "Paypal email must be valid")
    private String paypalEmail;

    @NotNull(message = "Card details cannot be empty")
    @Valid
    private CreditCardDetailsDTO cardDetails;
}
