package com.example.bankapp.exception.transaction_processing_exception;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(String message) {
    super(message);
    }
}
