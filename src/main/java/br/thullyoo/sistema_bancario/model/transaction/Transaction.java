package br.thullyoo.sistema_bancario.model.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DynamoDbBean
public class Transaction {

    private Long id;

    @NotNull(message = "Issued date cannot be null")
    private LocalDateTime issuedAt;

    @NotBlank(message = "Document sender cannot be blank")
    @Size(min = 2, max = 100, message = "Document sender must be between 2 and 100 characters")
    private String documentSender;

    @NotBlank(message = "Document receiver cannot be blank")
    @Size(min = 2, max = 100, message = "Document receiver must be between 2 and 100 characters")
    private String documentReceiver;

    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "0.01", message = "Value must be greater than zero")
    private BigDecimal value;

    public Transaction() {
    }

    @DynamoDbAttribute("transaction_id")
    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DynamoDbAttribute("issuedAt")
    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    @DynamoDbAttribute("sender_document")
    public String getDocumentSender() {
        return documentSender;
    }

    public void setDocumentSender(String documentSender) {
        this.documentSender = documentSender;
    }

    @DynamoDbAttribute("receiver_document")
    public String getDocumentReceiver() {
        return documentReceiver;
    }

    public void setDocumentReceiver(String documentReceiver) {
        this.documentReceiver = documentReceiver;
    }

    @DynamoDbAttribute("value")
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
