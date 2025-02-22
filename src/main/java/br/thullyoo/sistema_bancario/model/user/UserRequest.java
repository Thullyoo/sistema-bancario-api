package br.thullyoo.sistema_bancario.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record UserRequest(
        @Schema(
                description = "User's email address",
                example = "user@example.com",
                required = true
        )
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @Schema(
                description = "User's full name",
                example = "John Doe",
                required = true
        )
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @Schema(
                description = "User's password",
                example = "mySecurePassword123",
                required = true
        )
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password,

        @Schema(
                description = "User's document (CPF)",
                example = "12345678900",
                required = true
        )
        @NotBlank(message = "Document cannot be blank")
        @Pattern(regexp = "\\d{11}", message = "Document must be a valid 11-digit number")
        String document
) {}