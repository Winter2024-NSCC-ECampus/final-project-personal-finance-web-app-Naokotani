package com.web.finance.controller;

import com.web.finance.dto.account.AccountRequest;
import com.web.finance.dto.account.AccountResponse;
import com.web.finance.mapper.AccountMapper;
import com.web.finance.model.Account;
import com.web.finance.model.User;
import com.web.finance.repository.AccountRepository;
import com.web.finance.service.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CurrentUserService currentUserService;

    public AccountController(AccountRepository accountRepository, AccountMapper accountMapper, CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponse> res = accounts.stream().map(accountMapper::accountToAccountResponse).toList();
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest req){
        User user = currentUserService.getCurrentUser();
        Account account = accountMapper.accountRequestToAccount(req);
        account.setUser(user);
        accountRepository.save(account);
        return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody AccountRequest req, @RequestParam Long id) {
        Optional<Account> oldAccount = accountRepository.findById(id);
        oldAccount.ifPresent(a -> {
            if(!currentUserService.getCurrentUser().getId().equals(a.getId())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        });
        oldAccount.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account account = accountMapper.accountRequestToAccount(req);
        account.setId(id);
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
