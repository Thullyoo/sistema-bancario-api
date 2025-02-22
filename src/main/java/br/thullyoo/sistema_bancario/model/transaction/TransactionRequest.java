package br.thullyoo.sistema_bancario.model.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        @Schema(
                description = "Document number of the receiver",
                example = "12345678900",
                required = true
        )
        @NotNull(message = "Document cannot be null")
        @Size(min = 11, max = 11, message = "Document must be exactly 11 characters")
        @Pattern(regexp = "\\d{11}", message = "Document must be a valid 11-digit number")
        String documentReceiver,

        @Schema(
                description = "Value of the transaction",
                example = "100.00",
                required = true
        )
        @NotNull(message = "Value cannot be null")
        @DecimalMin(value = "0.01", message = "Value must be greater than or equal to 0.01")
        BigDecimal value) {
}
