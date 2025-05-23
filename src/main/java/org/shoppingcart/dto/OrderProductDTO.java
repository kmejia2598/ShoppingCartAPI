package org.shoppingcart.dto;

import lombok.Data;

@Data
public class OrderProductDTO {
    private Integer productId;
    private Integer quantity;
}
