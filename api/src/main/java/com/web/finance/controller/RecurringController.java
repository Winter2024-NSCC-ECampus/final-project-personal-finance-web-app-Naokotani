package com.web.finance.controller;

import com.web.finance.dto.recurring.RecurringRequest;
import com.web.finance.dto.recurring.RecurringResponse;
import com.web.finance.mapper.RecurringMapper;
import com.web.finance.model.RecurFrecuency;
import com.web.finance.model.Recurring;
import com.web.finance.repository.RecurringRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recurring")
public class RecurringController {
    private final RecurringRepository recurringRepository;
    private final RecurringMapper recurringMapper;

    public RecurringController(RecurringRepository recurringRepository, RecurringMapper recurringMapper) {
        this.recurringRepository = recurringRepository;
        this.recurringMapper = recurringMapper;
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
        recurring = recurringRepository.save(recurring);
        RecurringResponse res = recurringMapper.recurringToRecurringResponse(recurring);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        recurringRepository.deleteById(id);
        return new ResponseEntity<>("recurring expense deleted succesfully", HttpStatus.NO_CONTENT);
    }
}
