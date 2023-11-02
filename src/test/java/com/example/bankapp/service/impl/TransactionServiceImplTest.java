package com.example.bankapp.service.impl;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AccountActivityDTO;
import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.enums.TransactionStatus;
import com.example.bankapp.exception.transaction_processing_exception.SameAccountTransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class TransactionServiceImplTest {


    @Autowired
    TransactionServiceImpl transactionService;

    @Test
    void getTransactionByIdTest() {
        UUID transactionId = UUID.fromString("f1b872b6-eb29-4cb9-bf0f-36f57ad39562");
        TransactionDto transactionDto = transactionService.getTransactionById(transactionId);
        Assertions.assertEquals(transactionId.toString(), transactionDto.getTransactionId());
    }

    @Transactional
    @Rollback
    @Test
    void createTransactionTestPositiveCase() {
        TransactionCreationRequestDto transactionCreationRequestDto = new TransactionCreationRequestDto();
        transactionCreationRequestDto.setCreditAccountId("a2a4ff07-2cf9-4c7b-8e15-9e2dcb67fe8b");
        transactionCreationRequestDto.setDebitAccountId("a497500a-7cb1-4c15-982c-aa67f6d72f92");
        transactionCreationRequestDto.setAmount("1.00");
        transactionCreationRequestDto.setDescription("private transfer");
        TransactionDto transactionDto = transactionService.createTransaction(transactionCreationRequestDto);
        Assertions.assertNotNull(transactionDto.getTransactionId());
        Assertions.assertEquals(transactionCreationRequestDto.getAmount(), transactionDto.getAmount());
    }

    @Transactional
    @Rollback
    @Test
    void createTransactionTestNegativeCaseSameAccount() {
        TransactionCreationRequestDto transactionCreationRequestDto = new TransactionCreationRequestDto();
        transactionCreationRequestDto.setCreditAccountId("a2a4ff07-2cf9-4c7b-8e15-9e2dcb67fe8b");
        transactionCreationRequestDto.setDebitAccountId("a2a4ff07-2cf9-4c7b-8e15-9e2dcb67fe8b");
        transactionCreationRequestDto.setAmount("10000.00");
        transactionCreationRequestDto.setDescription("private transfer");
        Assertions.assertThrows(SameAccountTransactionException.class, () -> transactionService.createTransaction(transactionCreationRequestDto));
    }

    @Transactional
    @Rollback
    @Test
    void updateTransactionStatusToRejectedTest() {
        UUID transactionId = UUID.fromString("f1b872b6-eb29-4cb9-bf0f-36f57ad39562");
        TransactionDto transactionDto = transactionService.updateTransactionStatusToRejected(transactionId);
        Assertions.assertEquals(transactionDto.getTransactionStatus(), TransactionStatus.REJECTED.name());
    }

    @Test
    void getTransactionsForPeriodByClientTest() {
        UUID clientId = UUID.fromString("29f5b116-d35f-4171-5e4a-40eb48a63f34");
        AccountActivityDTO activityDTO = new AccountActivityDTO();
        activityDTO.setYear("2023");
        activityDTO.setMonth("10");
        Set<TransactionDto> transactionsForPeriodByClient = transactionService.getTransactionsForPeriodByClient(clientId, activityDTO);
        Assertions.assertNotNull(transactionsForPeriodByClient);


    }
}