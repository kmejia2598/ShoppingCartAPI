package org.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.dto.login.LoginRequest;
import org.shoppingcart.service.ClientService;
import org.shoppingcart.utils.GeneralMethos;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientService clientService;
    private final GeneralMethos gM;

    /**
     * Log in with the default username, obtained from the API External
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated  @RequestBody LoginRequest request, BindingResult bindingResult) {
        ResponseEntity<?> errores = gM.validarErrores(bindingResult);
        if (errores != null) return errores;

        return ResponseEntity.ok(clientService.login(request));
    }
}
