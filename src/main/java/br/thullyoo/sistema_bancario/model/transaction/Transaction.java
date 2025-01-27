package br.thullyoo.sistema_bancario.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Long id;

    private LocalDateTime issuedAt;

    private String documentSender;

    private String documentReceiver;

    private BigDecimal value;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getDocumentSender() {
        return documentSender;
    }

    public void setDocumentSender(String documentSender) {
        this.documentSender = documentSender;
    }

    public String getDocumentReceiver() {
        return documentReceiver;
    }

    public void setDocumentReceiver(String documentReceiver) {
        this.documentReceiver = documentReceiver;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
