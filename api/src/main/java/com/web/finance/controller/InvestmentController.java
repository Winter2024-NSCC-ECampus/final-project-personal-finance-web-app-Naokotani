package com.web.finance.controller;

import com.web.finance.dto.investment.InvestmentRequest;
import com.web.finance.mapper.InvestmentMapper;
import com.web.finance.model.Investment;
import com.web.finance.repository.InvestmentRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investment")
public class InvestmentController {
    private final InvestmentRepository investmentRepository;
    private final InvestmentMapper investmentMapper;

    public InvestmentController(InvestmentRepository investmentRepository, InvestmentMapper investmentMapper) {
        this.investmentRepository = investmentRepository;
        this.investmentMapper = investmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<Investment>> getInvestments(){
        return new ResponseEntity<>(investmentRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Investment> createInvestment(@RequestBody InvestmentRequest req) {
        Investment investment = investmentMapper.investmentRequestToInvestment(req);
        investmentRepository.save(investment);
        return new ResponseEntity<>(investment, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Investment> updateInvestment(@RequestBody InvestmentRequest req){
        Investment investment = investmentMapper.investmentRequestToInvestment(req);
        investment = investmentRepository.save(investment);
        return new ResponseEntity<>(investment, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInvestment(@RequestParam Long id){
        investmentRepository.deleteById(id);
        return new ResponseEntity<>("Investment Deleted", HttpStatus.NO_CONTENT);
    }
}
