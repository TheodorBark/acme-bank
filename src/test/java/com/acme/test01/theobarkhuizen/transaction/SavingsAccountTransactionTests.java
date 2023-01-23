package com.acme.test01.theobarkhuizen.transaction;

import com.acme.test01.theobarkhuizen.exception.MinimumDepositException;
import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class SavingsAccountTransactionTests {

    private SavingsAccountTransaction savingsAccountTransaction;

    @Test
    public void deposit() throws MinimumDepositException {
        savingsAccountTransaction = new SavingsAccountTransaction();
        savingsAccountTransaction.setMinimumSavingsAccountBalance(BigDecimal.valueOf(2000.00));

        BigDecimal newBalance = savingsAccountTransaction.deposit(BigDecimal.valueOf(0.00), BigDecimal.valueOf(2000.00));

        BigDecimal expectedBalance = BigDecimal.valueOf(2000.00);

        Assertions.assertEquals(expectedBalance, newBalance);
    }

    @Test(expected = WithdrawalAmountTooLargeException.class)
    public void withdrawAmountTooLarge() throws WithdrawalAmountTooLargeException {
        savingsAccountTransaction = new SavingsAccountTransaction();
        savingsAccountTransaction.setMinimumSavingsAccountBalance(BigDecimal.valueOf(2000.00));

        BigDecimal newBalance = savingsAccountTransaction.withdraw(BigDecimal.valueOf(2000.00), BigDecimal.valueOf(1000.00));
    }

    @Test
    public void withdraw() throws WithdrawalAmountTooLargeException {
        savingsAccountTransaction = new SavingsAccountTransaction();
        savingsAccountTransaction.setMinimumSavingsAccountBalance(BigDecimal.valueOf(2000.00));

        BigDecimal newBalance = savingsAccountTransaction.withdraw(BigDecimal.valueOf(5000.00), BigDecimal.valueOf(2000.00));

        BigDecimal expectedBalance = BigDecimal.valueOf(3000.00);

        Assertions.assertEquals(expectedBalance, newBalance);
    }
}