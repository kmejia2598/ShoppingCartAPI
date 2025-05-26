package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.ProductDTO;
import org.shoppingcart.dto.login.LoginRequest;
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

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productList() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/all/in-memory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productListMemory() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/get/in-memory/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> productByIdMemory(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductByIdMemory(id));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.createProductMemory(product));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.updateProductMemory(product));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProductMemory(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
