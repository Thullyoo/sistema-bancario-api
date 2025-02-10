package br.thullyoo.sistema_bancario.controllers;

import br.thullyoo.sistema_bancario.model.transaction.Transaction;
import br.thullyoo.sistema_bancario.model.transaction.TransactionRequest;
import br.thullyoo.sistema_bancario.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/register")
    public ResponseEntity<Transaction> register(@RequestBody TransactionRequest transactionRequest){
        Transaction transaction = transactionService.register(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
