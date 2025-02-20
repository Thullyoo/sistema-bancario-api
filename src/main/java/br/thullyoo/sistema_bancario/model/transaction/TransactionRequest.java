package br.thullyoo.sistema_bancario.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(String documentReceiver, BigDecimal value) {
}
