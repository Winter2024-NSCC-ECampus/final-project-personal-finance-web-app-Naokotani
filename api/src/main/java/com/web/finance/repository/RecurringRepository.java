package com.web.finance.repository;

import com.web.finance.model.RecurFrecuency;
import com.web.finance.model.Recurring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecurringRepository extends JpaRepository<Recurring, Long> {
}
