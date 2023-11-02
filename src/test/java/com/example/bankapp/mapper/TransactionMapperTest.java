package com.example.bankapp.mapper;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Transaction;
import com.example.bankapp.entity.enums.TransactionStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    void transactionToTransactionDtoTest() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        transaction.setDescription("Test");
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setDebitAccountId(new Account());
        transaction.setCreditAccountId(new Account());

        TransactionDto transactionDto = transactionMapper.transactionToTransactionDto(transaction);

        Assertions.assertEquals(transaction.getTransactionId().toString(), transactionDto.getTransactionId());
    }

    @Test
    void transactionRequestToTransactionTest() {
        TransactionCreationRequestDto requestDto = new TransactionCreationRequestDto();
        requestDto.setAmount("1000");
        requestDto.setDescription("Test");
        requestDto.setCreditAccountId(UUID.randomUUID().toString());
        requestDto.setDebitAccountId(UUID.randomUUID().toString());

        Transaction transaction = transactionMapper.transactionRequestToTransaction(requestDto);

        Assertions.assertEquals(requestDto.getAmount(), transaction.getAmount().toString());
    }

    @Test
    void transactionsToTransactionDto() {
        Set<Transaction> transactions = new HashSet<>();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        transaction.setDescription("Test");
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setDebitAccountId(new Account());
        transaction.setCreditAccountId(new Account());
        transactions.add(transaction);

        Set<TransactionDto> transactionDto = transactionMapper.transactionsToTransactionDto(transactions);

        Assertions.assertEquals(1, transactionDto.size());
    }
}