package com.web.finance.service;

import com.web.finance.dto.transaction.TransactionRequest;
import com.web.finance.dto.transaction.TransactionResponse;
import com.web.finance.mapper.TransactionMapper;
import com.web.finance.model.Account;
import com.web.finance.model.Transaction;
import com.web.finance.model.User;
import com.web.finance.repository.AccountRepository;
import com.web.finance.repository.TransactionRepository;
import com.web.finance.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final CurrentUserService userService;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(UserRepository userRepository, CurrentUserService userService, TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper) {
        this.userRepository = userRepository;
        this.userService =  userService;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    @Transactional
    public TransactionResponse processTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionMapper.transactionRequestToTransaction(transactionRequest);
        Account account = accountRepository.findById(transactionRequest.getAccountId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        User user = userService.getCurrentUser();
        if (user.getAccounts().contains(account)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        };
        transaction.setAccount(account);
        transaction.setUser(user);
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance().add(transaction.getAmount()));
        accountRepository.save(account);
        user.getTransactions().add(transaction);
        userRepository.save(user);
        return transactionMapper.transactionToTransactionResponse(transaction);
    }
}
