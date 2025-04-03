package com.web.finance.dto.account;

import lombok.Data;

@Data
public class AccountTypeRequest {
    private Long accountId;
    private String type;
}
