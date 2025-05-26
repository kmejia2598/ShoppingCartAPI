package org.shoppingcart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private Integer id;
    private Integer userId;
    private LocalDate date;
    private BigDecimal orderTotal;
    private Boolean paymentStatus = false;
    private List<OrderProductDTO> products;
}
