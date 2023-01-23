package com.acme.test01.theobarkhuizen.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract sealed class BankAccount permits CurrentAccount, SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    @Column(name = "minimum_balance")
    private BigDecimal minimumBalance;

    @Column(name = "maximum_withdraw_amount")
    private BigDecimal maximumWithdrawAmount;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "bank_account_type")
    private BankAccountType accountType;
}