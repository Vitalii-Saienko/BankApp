package com.example.bankapp.service.util;

import com.example.bankapp.dto.AccountActivityDTO;
import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public interface TransactionService {

    TransactionDto getTransactionById(UUID id);

    TransactionDto createTransaction(TransactionCreationRequestDto transaction);

    TransactionDto updateTransactionStatusToRejected(UUID uuid);

    Set<TransactionDto> getTransactionsForPeriodByClient(UUID uuid, AccountActivityDTO activityDTO);
}
