package com.acme.test01.theobarkhuizen.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {

    public static final String ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "Bank Account not found with account id: ";
    public static final String ACCOUNT_ALREADY_EXISTS_EXCEPTION_MESSAGE = "Account already exists with account id: ";
    public static final String OBJECT_NOT_PRESENT_MESSAGE = "BankAccount object is not present.";
    public static final String MIMINUM_DEPOSIT_AMOUNT_EXCEPTION_MESSAGE = "Minimum amount for opening a Savings Account must be greater than R";
    public static final String WITHDRAW_AMOUNT_TOO_LARGE_EXCEPTION_MESSAGE = "Insufficient funds to make a withdrawal.";
}