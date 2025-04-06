package com.web.finance.controller;

import com.web.finance.dto.transaction.TransactionRequest;
import com.web.finance.dto.transaction.TransactionResponse;
import com.web.finance.mapper.TransactionMapper;
import com.web.finance.model.Transaction;
import com.web.finance.repository.TransactionRepository;
import com.web.finance.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;


    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions() {
       List<Transaction> transactions = transactionRepository.findAll();
       List<TransactionResponse> res = transactions.stream()
               .map(transactionMapper::transactionToTransactionResponse).toList();
       return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest req) {
        TransactionResponse res = transactionService.processTransaction(req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@RequestParam Long id) {
        transactionRepository.deleteById(id);
        return new ResponseEntity<String>("Transaction Deleted", HttpStatus.NO_CONTENT);
    }
}
