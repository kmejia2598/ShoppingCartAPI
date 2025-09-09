package org.shoppingcart.utils;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RestControllerAdvice
public class GeneralMethos {

    public ResponseEntity<?> validarErrores(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String erroresConcatenados = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));  // une los mensajes con coma

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", erroresConcatenados);

            return ResponseEntity.badRequest().body(errorMap);
        }
        return null;
    }
}
