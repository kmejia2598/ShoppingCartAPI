package org.shoppingcart.dto.client;

import lombok.Data;

@Data
public class AddressDTO {
    private GeolocationDTO geolocation;
    private String city;
    private String street;
    private int number;
    private String zipcode;
}
