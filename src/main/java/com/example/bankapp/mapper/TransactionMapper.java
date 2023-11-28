package com.example.bankapp.mapper;

import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Set;


@Mapper(componentModel = "spring", injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
@Component
public interface TransactionMapper {

    @Mapping(target = "debitAccountId", expression = "java(mapAccountId(transaction.getDebitAccountId()))")
    @Mapping(target = "creditAccountId", expression = "java(mapAccountId(transaction.getCreditAccountId()))")
    TransactionDto transactionToTransactionDto(Transaction transaction);

    default String mapAccountId(Account accountId) {
        // Логика преобразования AccountId в строку
        return (accountId != null) ? accountId.getAccountUUID().toString() : null;
    }

    Transaction transactionRequestToTransaction(TransactionCreationRequestDto newTransaction);

    String map(Account value);

    Account map(String value);

    Set<TransactionDto> transactionsToTransactionDto(Set<Transaction> transactions);
}
