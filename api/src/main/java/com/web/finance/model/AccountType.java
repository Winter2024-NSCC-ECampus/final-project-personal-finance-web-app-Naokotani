package com.web.finance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountType {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    @OneToMany
    Set<Account> account;
}
