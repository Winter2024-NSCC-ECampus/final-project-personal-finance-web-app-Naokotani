package com.web.finance.dto.account;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    private String name;
    private String type;
    private BigDecimal balance;
}
