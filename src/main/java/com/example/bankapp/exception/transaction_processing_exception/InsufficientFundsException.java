package com.example.bankapp.exception.transaction_processing_exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
    super(message);
    }
}
