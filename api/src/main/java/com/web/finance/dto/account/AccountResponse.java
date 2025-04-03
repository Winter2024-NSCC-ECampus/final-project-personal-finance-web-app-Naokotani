package com.web.finance.dto.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private Long AccountTypeId;
    private String AccountTypeDescription;
    private BigDecimal balance;
}
