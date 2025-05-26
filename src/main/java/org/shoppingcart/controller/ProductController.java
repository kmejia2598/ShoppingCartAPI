package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.ProductDTO;
import org.shoppingcart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * Get list of API products
     */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productList() {
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Get list of products in memory
     */
    @GetMapping("/all/in-memory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productListMemory() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Get product by ID from API
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Get product by Id in memory
     */
    @GetMapping("/get/in-memory/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productByIdMemory(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductByIdMemory(id));
    }

    /**
     * Create a new product in memory
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.createProductMemory(product));
    }

    /**
     * Update a new product in memory
     */
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.updateProductMemory(product));
    }

    /**
     * Delete a new product in memory
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProductMemory(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
