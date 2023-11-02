package com.example.bankapp.exception.transaction_processing_exception;

public class SameAccountTransactionException extends RuntimeException {
    public SameAccountTransactionException(String message) {
        super(message);
    }
}
