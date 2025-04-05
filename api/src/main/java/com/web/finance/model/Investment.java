package com.web.finance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Investment {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private String type;
    private String description;
    private BigDecimal interestRate;
    private BigDecimal balance;
}
