package org.shoppingcart.dto.order;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderProductDTO {
    @NotNull(message = "The product ID is required")
    @Positive(message = "The product ID must be greater than 0")
    private Integer productId;

    @NotNull(message = "The quantity is required")
    @Positive(message = "The quantity must be greater than 0")
    private Integer quantity;
}
