package com.web.finance.mapper;

import com.web.finance.dto.recurring.RecurringRequest;
import com.web.finance.dto.recurring.RecurringResponse;
import com.web.finance.model.Recurring;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecurringMapper {
    Recurring recurringRequestToRecurring(RecurringRequest recurringRequest);
    RecurringResponse recurringToRecurringResponse(Recurring recurring);
}
