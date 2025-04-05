package com.web.finance.controller;

import com.web.finance.dto.account.AccountRequest;
import com.web.finance.dto.account.AccountResponse;
import com.web.finance.mapper.AccountMapper;
import com.web.finance.model.Account;
import com.web.finance.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountController(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponse> res = accounts.stream().map(accountMapper::accountToAccountResponse).toList();
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest req){
        Account account = accountMapper.accountRequestToAccount(req);
        accountRepository.save(account);
        return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountRequest req){
        Account account = accountMapper.accountRequestToAccount(req);
        account = accountRepository.save(account);
        AccountResponse res = accountMapper.accountToAccountResponse(account);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestParam Long id){
        accountRepository.deleteById(id);
        return new ResponseEntity<>("Account Deleted", HttpStatus.NO_CONTENT);
    }
}
