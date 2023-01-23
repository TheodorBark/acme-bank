package com.acme.test01.theobarkhuizen.exception;

public class WithdrawalAmountTooLargeException extends Exception {

    public WithdrawalAmountTooLargeException(String message) {
        super(message);
    }

    public WithdrawalAmountTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }
}