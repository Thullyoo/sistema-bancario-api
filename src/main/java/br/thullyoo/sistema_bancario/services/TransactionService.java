package br.thullyoo.sistema_bancario.services;

import br.thullyoo.sistema_bancario.model.transaction.Transaction;
import br.thullyoo.sistema_bancario.model.transaction.TransactionRequest;
import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transaction register(TransactionRequest request){
        User sender = userRepository.getUserByDocument(request.documentSender());
        User receiver = userRepository.getUserByDocument(request.documentReceiver());

        if (sender.getBalance().compareTo(request.value()) < 0){
            throw new RuntimeException("Sender doesn't have sufficient balance.");
        }

        Transaction transaction = new Transaction();


        transaction.setIssuedAt(LocalDateTime.now());
        transaction.setValue(request.value());
        transaction.setDocumentSender(sender.getDocument());
        transaction.setDocumentReceiver(receiver.getDocument());

        sender.setBalance(sender.getBalance().subtract(request.value()));
        receiver.setBalance(receiver.getBalance().add(request.value()));

        userRepository.updateUser(sender);
        userRepository.updateUser(receiver);

        return dynamoDbTemplate.save(transaction);
    }
    
    public List<Transaction> getAllTransactions(){
       SdkIterable<Transaction> transactions = dynamoDbTemplate.scan(ScanEnhancedRequest.builder().build(), Transaction.class).items();

       List<Transaction> transactionList = transactions.stream().toList();

       return transactionList;
    }

    public List<Transaction> getAllTransactionsByDocument(String document){
        List<Transaction> transactions  = dynamoDbTemplate.scan(ScanEnhancedRequest.builder().build(), Transaction.class)
                .items()
                .stream()
                .filter(t -> t.getDocumentSender().equals(document))
                .collect(Collectors.toList());

        return transactions;
    }
}
