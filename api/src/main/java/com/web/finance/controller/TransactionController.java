package com.web.finance.controller;

import com.web.finance.dto.transaction.TransactionRequest;
import com.web.finance.mapper.TransactionMapper;
import com.web.finance.model.Transaction;
import com.web.finance.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transation")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;


    public TransactionController(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
       List<Transaction> transactions = transactionRepository.findAll();
       return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody TransactionRequest req) {
        Transaction transaction = transactionMapper.transactionRequestToTransaction(req);
        transaction = transactionRepository.save(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest req) {
        Transaction transaction = transactionMapper.transactionRequestToTransaction(req);
        transaction = transactionRepository.save(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@RequestParam Long id) {
        transactionRepository.deleteById(id);

        return new ResponseEntity<String>("Transaction Deleted", HttpStatus.NO_CONTENT);
    }
}
