package com.web.finance.service;

import com.web.finance.model.Recurring;

import java.util.List;

public interface RecurringService {
    int processRecurring(List<Recurring> items);
}
