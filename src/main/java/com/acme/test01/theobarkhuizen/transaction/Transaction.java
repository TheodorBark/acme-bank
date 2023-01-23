package com.acme.test01.theobarkhuizen.transaction;

import com.acme.test01.theobarkhuizen.domain.BankAccount;
import com.acme.test01.theobarkhuizen.exception.MinimumDepositException;
import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;

import java.math.BigDecimal;

public interface Transaction {
    BigDecimal deposit(BigDecimal balance, BigDecimal amountToDeposit) throws MinimumDepositException;

    BigDecimal withdraw(BigDecimal balance, BigDecimal amountToWithdraw) throws WithdrawalAmountTooLargeException;
}