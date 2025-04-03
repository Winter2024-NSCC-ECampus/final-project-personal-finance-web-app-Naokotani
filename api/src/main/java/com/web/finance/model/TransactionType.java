package com.web.finance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionType {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    @OneToMany
    private Set<Transaction> transactions;
    @ManyToOne
    private User user;

}
