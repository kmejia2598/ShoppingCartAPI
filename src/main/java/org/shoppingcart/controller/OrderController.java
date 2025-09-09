package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.order.OrderDetailDTO;
import org.shoppingcart.service.OrderService;
import org.shoppingcart.utils.GeneralMethos;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final GeneralMethos gM;

    /**
     * Get list of Orders stored in memory
     */
    @GetMapping("/all/in-memory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> ordersAllMemory() {
        return ResponseEntity.ok(orderService.getAllOrdersMemory());
    }

    /**
     * Get order by Id in memory
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> orderByIdMemory(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderByIdMemory(id));
    }

    /**
     * Add a new order in memory
     */
    @PostMapping("/add/in-memory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<?> addOrderMemory(@Validated  @RequestBody OrderDetailDTO order, BindingResult bindingResult) {
        ResponseEntity<?> errores = gM.validarErrores(bindingResult);
        if (errores != null) return errores;

        return ResponseEntity.ok(orderService.addOrderMemory(order));
    }
}
