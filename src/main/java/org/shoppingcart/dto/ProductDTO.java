package org.shoppingcart.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;
}
