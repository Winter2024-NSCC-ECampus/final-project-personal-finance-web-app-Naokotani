package com.web.finance.dto.investment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentRequest {
    private Long typeId;
    private String description;
    private BigDecimal balance;
}
