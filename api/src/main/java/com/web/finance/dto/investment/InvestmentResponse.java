package com.web.finance.dto.investment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentResponse {
    private Long id;
    private String type;
    private String description;
    private BigDecimal balance;
}
