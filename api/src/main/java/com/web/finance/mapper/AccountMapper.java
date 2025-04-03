package com.web.finance.mapper;

import com.web.finance.dto.account.AccountRequest;
import com.web.finance.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account accountRequestToAccount(AccountRequest accountRequest);
}
