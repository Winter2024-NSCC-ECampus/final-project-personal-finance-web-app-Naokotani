package com.web.finance.model;

import jakarta.persistence.*;
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
public class Recurring {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private String description;
    private BigDecimal amount;
}
