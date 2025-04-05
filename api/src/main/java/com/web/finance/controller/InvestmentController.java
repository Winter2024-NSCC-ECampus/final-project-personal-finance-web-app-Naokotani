package com.web.finance.controller;

import com.web.finance.dto.investment.InvestmentRequest;
import com.web.finance.dto.investment.InvestmentResponse;
import com.web.finance.mapper.InvestmentMapper;
import com.web.finance.model.Investment;
import com.web.finance.repository.InvestmentRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<InvestmentResponse>> getInvestments(){
        List<Investment> investments = investmentRepository.findAll();
        List<InvestmentResponse> res = investments.stream().map(investmentMapper::investmentToInvestmentResponse).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InvestmentResponse> createInvestment(@RequestBody InvestmentRequest req) {
        Investment investment = investmentMapper.investmentRequestToInvestment(req);
        investment = investmentRepository.save(investment);
        InvestmentResponse res = investmentMapper.investmentToInvestmentResponse(investment);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<InvestmentResponse> updateInvestment(@RequestBody InvestmentRequest req, Long id){
        Investment oldInvestment = investmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Investment newInestment = investmentMapper.investmentRequestToInvestment(req);
        newInestment.setId(id);
        Investment investment = investmentRepository.save(newInestment);
        InvestmentResponse res = investmentMapper.investmentToInvestmentResponse(investment);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInvestment(@RequestParam Long id){
        investmentRepository.deleteById(id);
        return new ResponseEntity<>("Investment Deleted", HttpStatus.NO_CONTENT);
    }
}
