package com.web.finance.repository;

import com.web.finance.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

}
