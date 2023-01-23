package com.acme.test01.theobarkhuizen.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account")
public non-sealed class CurrentAccount extends BankAccount {

    @Column(name = "overdraft_limit")
    private BigDecimal overdraftLimit;
}