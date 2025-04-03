package com.web.finance.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private LocalDateTime transationDate;
    private String description;
    private BigDecimal amount;
}
