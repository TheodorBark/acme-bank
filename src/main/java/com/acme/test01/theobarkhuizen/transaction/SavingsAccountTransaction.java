package com.acme.test01.theobarkhuizen.transaction;

import com.acme.test01.theobarkhuizen.exception.MinimumDepositException;
import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.acme.test01.theobarkhuizen.exception.ExceptionMessage.MIMINUM_DEPOSIT_AMOUNT_EXCEPTION_MESSAGE;
import static com.acme.test01.theobarkhuizen.exception.ExceptionMessage.WITHDRAW_AMOUNT_TOO_LARGE_EXCEPTION_MESSAGE;

@Component
public class SavingsAccountTransaction implements Transaction {

    private BigDecimal minimumSavingsAccountBalance;

    @Value("${acme.bank.savings.min-account-balance:2000.00}")
    public void setMinimumSavingsAccountBalance(BigDecimal minimumSavingsAccountBalance) {
        this.minimumSavingsAccountBalance = minimumSavingsAccountBalance;
    }

    @Override
    public BigDecimal deposit(BigDecimal balance, BigDecimal amountToDeposit) throws MinimumDepositException {

        if (BigDecimal.ZERO.compareTo(balance) == 0) {
            if (amountToDeposit.compareTo(minimumSavingsAccountBalance) >= 0) {
                return balance.add(amountToDeposit);
            }
            else {
                throw new MinimumDepositException(MIMINUM_DEPOSIT_AMOUNT_EXCEPTION_MESSAGE + minimumSavingsAccountBalance.toPlainString());
            }
        }

        return balance.add(amountToDeposit);
    }

    @Override
    public BigDecimal withdraw(BigDecimal balance, BigDecimal amountToWithdraw) throws WithdrawalAmountTooLargeException {

        if (amountToWithdraw.compareTo(balance) <= 0
                && balance.subtract(amountToWithdraw).compareTo(minimumSavingsAccountBalance) >= 0) {

            return balance.subtract(amountToWithdraw);
        }

        throw new WithdrawalAmountTooLargeException(WITHDRAW_AMOUNT_TOO_LARGE_EXCEPTION_MESSAGE);
    }
}