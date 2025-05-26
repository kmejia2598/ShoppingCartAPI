package org.shoppingcart.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;
}
