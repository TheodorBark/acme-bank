package com.acme.test01.theobarkhuizen.transaction;

import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.acme.test01.theobarkhuizen.exception.ExceptionMessage.WITHDRAW_AMOUNT_TOO_LARGE_EXCEPTION_MESSAGE;

@Slf4j
@Component
public class CurrentAccountTransaction implements Transaction {

    private BigDecimal maximumOverdraftLimit;

    @Value("${acme.bank.current.max-overdraft-limit:100000.00}")
    public void setMaximumOverdraftLimit(BigDecimal maximumOverdraftLimit) {
        this.maximumOverdraftLimit = maximumOverdraftLimit;
    }

    @Override
    public BigDecimal deposit(BigDecimal balance, BigDecimal amountToDeposit) {
        return balance.add(amountToDeposit);
    }

    @Override
    public BigDecimal withdraw(BigDecimal balance, BigDecimal amountToWithdraw) throws WithdrawalAmountTooLargeException {

        if (amountToWithdraw.compareTo(balance.add(maximumOverdraftLimit)) <= 0) {
            return balance.subtract(amountToWithdraw);
        }

        throw new WithdrawalAmountTooLargeException(WITHDRAW_AMOUNT_TOO_LARGE_EXCEPTION_MESSAGE);
    }
}