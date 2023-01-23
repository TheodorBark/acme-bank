package com.acme.test01.theobarkhuizen.service;

import com.acme.test01.theobarkhuizen.exception.MinimumDepositException;
import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.theobarkhuizen.exception.AccountNotFoundException;

import java.math.BigDecimal;

public interface AccountService {
    void openSavingsAccount(Long accountId, BigDecimal amountToDeposit) throws MinimumDepositException;
    void openCurrentAccount(Long accountId, BigDecimal amountToDeposit) throws MinimumDepositException;
    void withdraw(Long accountId, BigDecimal amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException;
    void deposit(Long accountId, BigDecimal amountToDeposit) throws AccountNotFoundException, MinimumDepositException;
}