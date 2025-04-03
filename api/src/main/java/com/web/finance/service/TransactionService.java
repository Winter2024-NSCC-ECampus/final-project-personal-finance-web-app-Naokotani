package com.web.finance.service;

import com.web.finance.dto.transaction.TransactionRequest;
import com.web.finance.dto.transaction.TransactionResponse;
import com.web.finance.model.Transaction;
import com.web.finance.model.User;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionResponse processTransaction(TransactionRequest transactionRequest);
}
