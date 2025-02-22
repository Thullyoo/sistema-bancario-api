package br.thullyoo.sistema_bancario.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Schema(
                description = "User's document (CPF, CNPJ, etc.)",
                example = "12345678900",
                required = true
        )
        @NotNull(message = "Document cannot be null")
        @Size(min = 11, max = 11, message = "Document must be exactly 11 characters")
        @Pattern(regexp = "\\d{11}", message = "Document must be a valid 11-digit number")
        String document,

        @Schema(
                description = "User's password",
                example = "mySecurePassword123",
                required = true
        )
        @NotNull(message = "Password cannot be null")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        String password
){}

