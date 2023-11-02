package com.example.bankapp.exception.transaction_processing_exception;

public class DataForTransactionNotValidException extends RuntimeException {
    public DataForTransactionNotValidException(String message) {
        super(message);
    }
}

