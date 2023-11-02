package com.example.bankapp.exception.database_exception;

public class DatabaseAccessException extends RuntimeException {

    public DatabaseAccessException(String message) {
        super(message);
    }
}
