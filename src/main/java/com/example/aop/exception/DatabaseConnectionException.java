package com.example.aop.exception;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
