package com.web.finance.controller;

import com.web.finance.dto.recurring.RecurringRequest;
import com.web.finance.dto.recurring.RecurringResponse;
import com.web.finance.mapper.RecurringMapper;
import com.web.finance.model.Account;
import com.web.finance.model.RecurFrecuency;
import com.web.finance.model.Recurring;
import com.web.finance.model.User;
import com.web.finance.repository.AccountRepository;
import com.web.finance.repository.RecurringRepository;
import com.web.finance.service.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recurring")
public class RecurringController {
    private final RecurringRepository recurringRepository;
    private final RecurringMapper recurringMapper;
    private final CurrentUserService currentUserService;
    private final AccountRepository accountRepository;

    public RecurringController(RecurringRepository recurringRepository, RecurringMapper recurringMapper, CurrentUserService currentUserService, AccountRepository accountRepository) {
        this.recurringRepository = recurringRepository;
        this.recurringMapper = recurringMapper;
        this.currentUserService = currentUserService;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public ResponseEntity<List<RecurringResponse>> findAll() {
        List<Recurring> recurring = recurringRepository.findAll();
        List<RecurringResponse> res = recurring.stream().map(recurringMapper::recurringToRecurringResponse).toList();
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<RecurringResponse> create(@RequestBody RecurringRequest recurringRequest) {
        Recurring recurring = recurringMapper.recurringRequestToRecurring(recurringRequest);
        User user = currentUserService.getCurrentUser();
        Account account = accountRepository.findById(recurringRequest.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        recurring.setAccount(account);
        recurring.setUser(user);
        recurring = recurringRepository.save(recurring);
        RecurringResponse res = recurringMapper.recurringToRecurringResponse(recurring);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RecurringResponse> update(@RequestBody RecurringRequest recurringRequest, @RequestParam Long id) {
        Optional<Recurring> oldRecurring = recurringRepository.findById(id);
        oldRecurring.ifPresent(r -> {
            if(!currentUserService.getCurrentUser().getId().equals(r.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            };});
        oldRecurring.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Recurring recurring = recurringMapper.recurringRequestToRecurring(recurringRequest);
        recurring.setId(id);
        recurring = recurringRepository.save(recurring);
        RecurringResponse res = recurringMapper.recurringToRecurringResponse(recurring);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        recurringRepository.deleteById(id);
        return new ResponseEntity<>("recurring expense deleted succesfully", HttpStatus.NO_CONTENT);
    }
}
