package com.web.finance.dto.account;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequest {
    private Long typeId;
    private BigDecimal balance;
}
