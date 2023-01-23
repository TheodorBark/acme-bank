package com.acme.test01.theobarkhuizen.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public non-sealed class SavingsAccount extends BankAccount {
    // Add Savings Account specific properties
    // Currently is only used for distinguishing types
}