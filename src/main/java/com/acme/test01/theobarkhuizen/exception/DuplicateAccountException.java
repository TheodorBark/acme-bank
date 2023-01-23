package com.acme.test01.theobarkhuizen.exception;

public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String message) {
        super(message);
    }

    public DuplicateAccountException(String messsge, Throwable cause) {
        super(messsge, cause);
    }
}