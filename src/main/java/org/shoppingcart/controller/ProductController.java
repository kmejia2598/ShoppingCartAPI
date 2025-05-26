package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> productList() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/all/in-memory")
    public ResponseEntity<?> productListMemory() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> productById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/get/in-memory/{id}")
    public ResponseEntity<?> productByIdMemory(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductByIdMemory(id));
    }
}
