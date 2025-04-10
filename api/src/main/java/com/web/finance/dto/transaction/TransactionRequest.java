package com.web.finance.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private Long accountId;
    private String description;
    private BigDecimal amount;
}
