package com.example.bankapp.controller;

import com.example.bankapp.dto.AccountActivityDTO;
import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.service.util.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public TransactionDto getTransactionById(@PathVariable("id") String id){
        return transactionService.getTransactionById(UUID.fromString(id));
    }

    @PostMapping("/create")
    public TransactionDto createTransaction(@Valid @RequestBody TransactionCreationRequestDto newTransaction){
        return transactionService.createTransaction(newTransaction);
    }

    @PutMapping("/update/{id}")
    public TransactionDto updateTransactionStatusToRejected(@PathVariable("id") String id){
        return transactionService.updateTransactionStatusToRejected(UUID.fromString(id));
    }

    @GetMapping("/show-all-for-period-by-client/{id}")
    public Set<TransactionDto> getTransactionsForPeriodByClient(@PathVariable("id") String id, @Valid @RequestBody AccountActivityDTO activityDTO){
        return transactionService.getTransactionsForPeriodByClient(UUID.fromString(id), activityDTO);
    }
}
