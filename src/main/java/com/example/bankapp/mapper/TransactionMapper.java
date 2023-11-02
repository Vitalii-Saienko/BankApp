package com.example.bankapp.mapper;

import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Transaction;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Set;


@Mapper(componentModel = "spring", injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
@Component
public interface TransactionMapper {

    TransactionDto transactionToTransactionDto(Transaction transaction);

    Transaction transactionRequestToTransaction(TransactionCreationRequestDto newTransaction);

    String map(Account value);

    Account map(String value);

    Set<TransactionDto> transactionsToTransactionDto(Set<Transaction> transactions);
}
