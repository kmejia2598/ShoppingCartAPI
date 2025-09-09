package org.shoppingcart.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDetailDTO {
    @NotNull(message = "The user ID is required")
    @Positive(message = "The user ID must be greater than 0")
    private Integer userId;

    @NotNull(message = "The order date is required")
    @FutureOrPresent(message = "The order date cannot be in the past")
    private LocalDate date;

    @NotEmpty(message = "The order must contain at least one product")
    @Valid
    private List<OrderProductDTO> products;
}
