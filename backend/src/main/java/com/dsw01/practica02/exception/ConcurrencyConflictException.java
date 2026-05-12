package com.dsw01.practica02.exception;

public class ConcurrencyConflictException extends RuntimeException {

    public ConcurrencyConflictException(String message) {
        super(message);
    }
}
