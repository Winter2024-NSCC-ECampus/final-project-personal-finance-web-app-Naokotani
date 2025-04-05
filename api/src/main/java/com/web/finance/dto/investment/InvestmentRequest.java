package com.web.finance.dto.investment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentRequest {
    private String type;
    private String description;
    private BigDecimal balance;
}
