package com.web.finance.controller;

import com.web.finance.dto.investment.InvestmentRequest;
import com.web.finance.dto.investment.InvestmentResponse;
import com.web.finance.mapper.InvestmentMapper;
import com.web.finance.model.Investment;
import com.web.finance.repository.InvestmentRepository;
import com.web.finance.service.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/investment")
public class InvestmentController {
    private final InvestmentRepository investmentRepository;
    private final InvestmentMapper investmentMapper;
    private final CurrentUserService currentUserService;

    public InvestmentController(InvestmentRepository investmentRepository, InvestmentMapper investmentMapper, CurrentUserService currentUserService) {
        this.investmentRepository = investmentRepository;
        this.investmentMapper = investmentMapper;
        this.currentUserService = currentUserService;
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
        Optional<Investment> oldInvestment = investmentRepository.findById(id);
        oldInvestment.ifPresent(i -> {
            if(!currentUserService.getCurrentUser().getId().equals(i.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        });
        investmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
