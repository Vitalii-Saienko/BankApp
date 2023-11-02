package com.example.bankapp.exception.transaction_processing_exception;

public class ClientNotActiveException extends RuntimeException {
    public ClientNotActiveException(String message) {
        super(message);
    }
}

