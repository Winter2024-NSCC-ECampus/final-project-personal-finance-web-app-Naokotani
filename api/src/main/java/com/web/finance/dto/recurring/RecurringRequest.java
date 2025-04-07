package com.web.finance.dto.recurring;

import com.web.finance.model.RecurFrecuency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecurringRequest {
    private String description;
    private String frequency;
    private Long accountId;
    private BigDecimal amount;
}
