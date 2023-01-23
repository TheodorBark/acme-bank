package com.acme.test01.theobarkhuizen.service;

import com.acme.test01.theobarkhuizen.domain.BankAccount;
import com.acme.test01.theobarkhuizen.domain.BankAccountType;
import com.acme.test01.theobarkhuizen.domain.CurrentAccount;
import com.acme.test01.theobarkhuizen.domain.SavingsAccount;
import com.acme.test01.theobarkhuizen.exception.AccountNotFoundException;
import com.acme.test01.theobarkhuizen.exception.DuplicateAccountException;
import com.acme.test01.theobarkhuizen.exception.MinimumDepositException;
import com.acme.test01.theobarkhuizen.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.theobarkhuizen.repository.BankAccountRepository;
import com.acme.test01.theobarkhuizen.transaction.CurrentAccountTransaction;
import com.acme.test01.theobarkhuizen.transaction.SavingsAccountTransaction;
import com.acme.test01.theobarkhuizen.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.acme.test01.theobarkhuizen.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Value("${acme.bank.savings.min-account-balance:2000.00}")
    private BigDecimal minimumSavingsAccountBalance;

    @Value("${acme.bank.current.max-overdraft-limit:100000.00}")
    private BigDecimal maximumOverdraftLimit;

    private final BankAccountRepository bankAccountRepository;
    private final CurrentAccountTransaction currentAccountTransaction;
    private final SavingsAccountTransaction savingsAccountTransaction;

    @Override
    public void openSavingsAccount(Long accountId, BigDecimal amountToDeposit) throws MinimumDepositException {

        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountId(accountId);

        if (bankAccount.isEmpty()) {
            BankAccount newSavingsAccount = new SavingsAccount();
            newSavingsAccount.setAccountId(accountId);
            newSavingsAccount.setAccountNumber(UUID.randomUUID().toString());
            newSavingsAccount.setAccountType(BankAccountType.SAVINGS);
            newSavingsAccount.setCustomerNumber(UUID.randomUUID().toString());
            newSavingsAccount.setInterestRate(7.5d);
            newSavingsAccount.setMinimumBalance(minimumSavingsAccountBalance);
            newSavingsAccount.setMaximumWithdrawAmount(BigDecimal.valueOf(100.00));
            newSavingsAccount.setAccountBalance(BigDecimal.valueOf(0.00));

            bankAccountRepository.save(newSavingsAccount);

            Transaction transaction = this.getTransactionType(BankAccountType.SAVINGS);
            newSavingsAccount.setAccountBalance(transaction.deposit(newSavingsAccount.getAccountBalance(),
                    amountToDeposit));

            bankAccountRepository.save(newSavingsAccount);
        }
        else {
            throw new DuplicateAccountException(ACCOUNT_ALREADY_EXISTS_EXCEPTION_MESSAGE + accountId);
        }
    }

    @Override
    public void openCurrentAccount(Long accountId, BigDecimal amountToDeposit) throws MinimumDepositException {

        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountId(accountId);

        if (bankAccount.isEmpty()) {
            CurrentAccount newCurrentAccount = new CurrentAccount();
            newCurrentAccount.setAccountId(accountId);
            newCurrentAccount.setAccountNumber(UUID.randomUUID().toString());
            newCurrentAccount.setAccountType(BankAccountType.CURRENT);
            newCurrentAccount.setCustomerNumber(UUID.randomUUID().toString());
            newCurrentAccount.setInterestRate(7.5d);
            newCurrentAccount.setMinimumBalance(minimumSavingsAccountBalance);
            newCurrentAccount.setMaximumWithdrawAmount(BigDecimal.valueOf(100.00));
            newCurrentAccount.setAccountBalance(BigDecimal.valueOf(0.00));
            newCurrentAccount.setOverdraftLimit(maximumOverdraftLimit);

            bankAccountRepository.save(newCurrentAccount);

            Transaction transaction = this.getTransactionType(BankAccountType.CURRENT);
            newCurrentAccount.setAccountBalance(transaction.deposit(newCurrentAccount.getAccountBalance(),
                    amountToDeposit));

            bankAccountRepository.save(newCurrentAccount);
        }
        else {
            throw new DuplicateAccountException(ACCOUNT_ALREADY_EXISTS_EXCEPTION_MESSAGE + accountId);
        }
    }

    @Transactional
    @Override
    public void withdraw(Long accountId, BigDecimal amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {

        Optional<BankAccount> bankAccount = Optional.ofNullable(bankAccountRepository.findByAccountId(accountId))
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE + accountId));

        if (bankAccount.isPresent()) {
            Transaction transaction = this.getTransactionType(bankAccount.get().getAccountType());
            bankAccount.get().setAccountBalance(transaction.withdraw(bankAccount.get().getAccountBalance(), amountToWithdraw));
            bankAccountRepository.saveAndFlush(bankAccount.get());
        }
        else {
            log.info(OBJECT_NOT_PRESENT_MESSAGE);
        }
    }

    @Transactional
    @Override
    public void deposit(Long accountId, BigDecimal amountToDeposit) throws AccountNotFoundException, MinimumDepositException {

        Optional<BankAccount> bankAccount = Optional.ofNullable(bankAccountRepository.findByAccountId(accountId))
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE + accountId));

        if (bankAccount.isPresent()) {
            Transaction transaction = this.getTransactionType(bankAccount.get().getAccountType());
            bankAccount.get().setAccountBalance(transaction.deposit(bankAccount.get().getAccountBalance(), amountToDeposit));
            bankAccountRepository.saveAndFlush(bankAccount.get());
        }
        else {
            log.info(OBJECT_NOT_PRESENT_MESSAGE);
        }
    }

    public Transaction getTransactionType(BankAccountType bankAccountType) {

        if (BankAccountType.SAVINGS.equals(bankAccountType)) {
            return savingsAccountTransaction;
        }
        else if (BankAccountType.CURRENT.equals(bankAccountType)) {
            return currentAccountTransaction;
        }
        else {
            throw new RuntimeException(String.format("Could not identify implementation for the bank account type [%s]",
                    bankAccountType.name()));
        }
    }
}