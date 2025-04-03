package com.web.finance.dto.recurring;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecurringRequest {
    private String description;
    private BigDecimal amount;
}
