package com.web.finance.dto.recurring;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecurringResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
}
