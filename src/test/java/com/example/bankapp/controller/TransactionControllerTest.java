package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AccountActivityDTO;
import com.example.bankapp.dto.TransactionCreationRequestDto;
import com.example.bankapp.service.impl.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionServiceImpl transactionService;

    @Autowired
    TransactionController transactionController;

    @Test
    void getTransactionByIdTestValidRequest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(get("/transaction/" + validUuid))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTransactionByIdTestInvalidRequest() {
        String invalidUuid = "not-a-valid-uuid";
        try {
            mockMvc.perform(get("/transaction/" + invalidUuid))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createTransactionTestValidRequest() {
        TransactionCreationRequestDto newTransactionRequest = new TransactionCreationRequestDto();
        newTransactionRequest.setAmount("100");
        newTransactionRequest.setDescription("test");
        newTransactionRequest.setCreditAccountId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        newTransactionRequest.setDebitAccountId("a10e6991-c16a-4f9b-9f04-6fda0510d611");
        try {
            mockMvc.perform(post("/transaction/create")
                    .content(new ObjectMapper().writeValueAsString(newTransactionRequest))
                    .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createTransactionTestInvalidRequest() {
        TransactionCreationRequestDto newTransactionRequest = new TransactionCreationRequestDto();
        try {
            mockMvc.perform(post("/transaction/create")
                            .content(String.valueOf(newTransactionRequest))
                            .contentType("application/json"))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateTransactionStatusToRejectedTest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(put("/transaction/update/" + validUuid)
                            .contentType("application/json"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTransactionsForPeriodByClientTest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        AccountActivityDTO accountActivityDTO = new AccountActivityDTO();
        accountActivityDTO.setYear("2022");
        accountActivityDTO.setMonth("11");

        try {
            mockMvc.perform(get("/transaction/show-all-for-period-by-client/" + validUuid)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountActivityDTO)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}