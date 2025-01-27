package br.thullyoo.sistema_bancario.model.user;

import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,

        @NotBlank(message = "Document cannot be blank")
        @Pattern(regexp = "\\d{11}", message = "Document must be a valid 11-digit number")
        String document
) {
}