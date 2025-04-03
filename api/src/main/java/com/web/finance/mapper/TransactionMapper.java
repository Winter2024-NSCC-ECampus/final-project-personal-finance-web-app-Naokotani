package com.web.finance.mapper;

import com.web.finance.dto.investment.InvestmentRequest;
import com.web.finance.dto.transaction.TransactionRequest;
import com.web.finance.dto.transaction.TransactionResponse;
import com.web.finance.model.Investment;
import com.web.finance.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction transactionRequestToTransaction(TransactionRequest transactionRequest);
    TransactionResponse transactionToTransactionResponse(Transaction transaction);
}
