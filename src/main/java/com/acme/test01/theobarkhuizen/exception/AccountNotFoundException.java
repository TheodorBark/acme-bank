package com.acme.test01.theobarkhuizen.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String messsge, Throwable cause) {
        super(messsge, cause);
    }
}