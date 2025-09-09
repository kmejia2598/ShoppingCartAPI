package org.shoppingcart.dto.login;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "The username is required")
    @Size(min = 4, max = 50, message = "The username must be between 4 and 50 characters")
    private String username;

    @NotBlank(message = "The password is required")
    @Size(min = 8, max = 100, message = "The password must have at least 8 characters")
    private String password;
}
