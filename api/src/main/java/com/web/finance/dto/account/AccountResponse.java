package com.web.finance.dto.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private String name;
    private String type;
    private BigDecimal balance;
}
