package br.thullyoo.sistema_bancario.controllers;

import br.thullyoo.sistema_bancario.model.transaction.Transaction;
import br.thullyoo.sistema_bancario.model.transaction.TransactionRequest;
import br.thullyoo.sistema_bancario.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@SecurityRequirement(name = "BearerAuth")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Register transaction", description = "Method to register a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction registered successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to use this service"),
    })
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Transaction> register(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.register(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @Operation(summary = "Get all transactions", description = "Method to retrieve all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to use this service"),
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllTransactions());
    }

    @Operation(summary = "Get all transactions by user", description = "Method to retrieve all transactions for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to use this service"),
    })
    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity<List<Transaction>> getAllTransactionsByUser() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllTransactionsByUser());
    }
}
