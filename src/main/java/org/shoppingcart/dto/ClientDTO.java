package org.shoppingcart.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String phone;
    private NameDTO name;
    private AddressDTO address;
}