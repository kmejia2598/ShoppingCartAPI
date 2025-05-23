package org.shoppingcart.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDetailDTO {
    private Integer id;
    private ClientDTO client;
    private LocalDate date;
    private List<OrderProductDTO> products;
}
