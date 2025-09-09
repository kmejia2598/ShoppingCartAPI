package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.dto.product.ProductDTO;
import org.shoppingcart.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RestTemplate restTemplate;
    private final MemoryDB memoryDB;
    @Value("${external.api.fakestore.url}")
    private String urlAPI;


    public List<ProductDTO> getProducts() {
        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(urlAPI + "/products", ProductDTO[].class);
        ProductDTO[] products = response.getBody();

        if (products == null || products.length == 0) {
            throw new NotFoundException("There are no products available.");
        }
        return Arrays.asList(products);
    }

    public ProductDTO getProductById(Integer id) {
        try {
            ResponseEntity<ProductDTO> response = restTemplate.getForEntity(urlAPI + "/products/" + id, ProductDTO.class);
            ProductDTO product = response.getBody();

            if (product == null) {
                throw new NotFoundException("Product with ID " + id + " not found");
            }
            return product;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Product with ID " + id + " not found");
        }
    }

    public Collection<ProductDTO> getAllProducts() {
        return memoryDB.getAllProducts();
    }

    public ProductDTO getProductByIdMemory(Integer id) {
        Optional<ProductDTO> product = memoryDB.getProductById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NotFoundException("Product with ID " + id + " not found");
        }
    }

    public ProductDTO createProductMemory(ProductDTO product) {
        return memoryDB.addProduct(product);
    }

    public ProductDTO updateProductMemory(ProductDTO product) {
        return memoryDB.updateProduct(product);
    }

    public boolean deleteProductMemory(Integer id) {
        return memoryDB.deleteProduct(id);
    }
}
