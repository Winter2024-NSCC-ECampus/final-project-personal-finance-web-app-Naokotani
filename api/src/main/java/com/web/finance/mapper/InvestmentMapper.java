package com.web.finance.mapper;

import com.web.finance.dto.account.AccountRequest;
import com.web.finance.dto.investment.InvestmentRequest;
import com.web.finance.dto.investment.InvestmentResponse;
import com.web.finance.model.Account;
import com.web.finance.model.Investment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvestmentMapper {
    Investment investmentRequestToInvestment(InvestmentRequest investmentRequest);
    InvestmentResponse investmentToInvestmentResponse(Investment investment);
}