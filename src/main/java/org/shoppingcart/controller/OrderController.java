package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.OrderDTO;
import org.shoppingcart.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all/in-memory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> ordersAllMemory() {
        return ResponseEntity.ok(orderService.getAllOrdersMemory());
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> orderByIdMemory(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderByIdMemory(id));
    }

    @PostMapping("/add/in-memory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> addOrderMemory(@RequestBody OrderDTO order) {
        return ResponseEntity.ok(orderService.addOrderMemory(order));
    }
}
