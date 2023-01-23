package com.acme.test01.theobarkhuizen.transaction;

import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class CurrentAccountTransactionTests {

    private CurrentAccountTransaction currentAccountTransaction;

    @Test
    public void deposit() {
        currentAccountTransaction = new CurrentAccountTransaction();
        BigDecimal newBalance = currentAccountTransaction.deposit(BigDecimal.valueOf(2000.00),
                BigDecimal.valueOf(1500.00));

        BigDecimal expectedBalance = BigDecimal.valueOf(3500.00);

        Assertions.assertEquals(expectedBalance, newBalance);
    }

    @Test(expected = WithdrawalAmountTooLargeException.class)
    public void withdrawAmountTooLarge() throws WithdrawalAmountTooLargeException {
        currentAccountTransaction = new CurrentAccountTransaction();
        currentAccountTransaction.setMaximumOverdraftLimit(BigDecimal.valueOf(100000.00));

        BigDecimal newBalance = currentAccountTransaction.withdraw(BigDecimal.valueOf(2000.00),
                BigDecimal.valueOf(150000.00));
    }

    @Test
    public void withdraw() throws WithdrawalAmountTooLargeException {
        currentAccountTransaction = new CurrentAccountTransaction();
        currentAccountTransaction.setMaximumOverdraftLimit(BigDecimal.valueOf(100000.00));

        BigDecimal newBalance = currentAccountTransaction.withdraw(BigDecimal.valueOf(2000.00),
                BigDecimal.valueOf(1500.00));

        BigDecimal expectedBalance = BigDecimal.valueOf(500.00);

        Assertions.assertEquals(expectedBalance, newBalance);
    }
}