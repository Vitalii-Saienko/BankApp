package com.example.bankapp.exception.exception_handler;

import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.exception.transaction_processing_exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    @ExceptionHandler(DatabaseAccessException.class)
    public ResponseEntity<String> handleDatabaseAccessException(DatabaseAccessException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(500))
                .body(e.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }

    @ExceptionHandler(CurrencyMismatchException.class)
    public ResponseEntity<String> handleCurrencyMismatchException(CurrencyMismatchException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<String> handleAccountNotActiveException(AccountNotActiveException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }

    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<String> handleClientNotActiveException(ClientNotActiveException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }

    @ExceptionHandler(DataForTransactionNotValidException.class)
    public ResponseEntity<String> handleDataForTransactionNotValidException(DataForTransactionNotValidException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }

    @ExceptionHandler(SameAccountTransactionException.class)
    public ResponseEntity<String> handleSameAccountTransactionException(SameAccountTransactionException e) {
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(e.getMessage());
    }
}
