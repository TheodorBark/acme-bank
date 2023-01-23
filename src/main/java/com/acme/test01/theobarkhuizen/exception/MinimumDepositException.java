package com.acme.test01.theobarkhuizen.exception;

public class MinimumDepositException extends Exception {

    public MinimumDepositException(String message) {
        super(message);
    }

    public MinimumDepositException(String message, Throwable cause) {
        super(message, cause);
    }
}
