package com.example.aop.exception;

public class NetworkTimeoutException extends RuntimeException {
    public NetworkTimeoutException(String message) {
        super(message);
    }
}

