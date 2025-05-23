package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.ProductDTO;
import org.shoppingcart.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;

    public List<ProductDTO> getProducts() {
        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products", ProductDTO[].class);
        ProductDTO[] products = response.getBody();

        if (products == null || products.length == 0) {
            throw new NotFoundException("No hay productos disponibles");
        }
        return Arrays.asList(products);
    }

    public ProductDTO getProductById(Integer id){
        try {
            ResponseEntity<ProductDTO> response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, ProductDTO.class);
            ProductDTO product = response.getBody();

            if (product == null) {
                throw new NotFoundException("Producto con ID " + id + " no encontrado");
            }
            return product;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Producto con ID " + id + " no encontrado");
        }
    }
}
