package org.shoppingcart.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RatingDTO {

    @DecimalMin(value = "0.0", message = "The rate must be at least 0")
    @DecimalMax(value = "5.0", message = "The rate must be at most 5")
    private Double rate;

    @Positive(message = "The count must be greater than 0")
    private Integer count;
}
