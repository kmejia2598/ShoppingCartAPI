package org.shoppingcart.dto.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;

    @NotBlank(message = "The product title is required")
    @Size(min = 3, max = 100, message = "The title must be between 3 and 100 characters")
    private String title;

    @NotNull(message = "The product price is required")
    @Positive(message = "The price must be greater than 0")
    private Double price;

    @NotBlank(message = "The description is required")
    @Size(max = 500, message = "The description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "The category is required")
    private String category;

    @NotBlank(message = "The image URL is required")
    private String image;

    @NotNull(message = "The rating is required")
    @Valid
    private RatingDTO rating;
}
