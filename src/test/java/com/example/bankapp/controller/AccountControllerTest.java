package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountServiceImpl accountService;

    @Autowired
    AccountController accountController;

    @Test
    void getAccountByIdTestCaseValidId() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        AccountDto accountDto = new AccountDto();
        Mockito.when(accountService.getAccountById(UUID.fromString(validUuid)))
                .thenReturn(accountDto);
        AccountDto account = accountController.getAccountById(validUuid);
        Assertions.assertEquals(accountDto, account);
    }

    @Test
    void getAccountByIdTestCaseInvalidId() {
        String invalidId = "a00e0000-c00a-0f0b-0f00-00000000d000";
        Mockito.when(accountService.getAccountById(UUID.fromString(invalidId)))
                .thenThrow(DatabaseAccessException.class);
        Assertions.assertThrows(DatabaseAccessException.class, () -> accountController.getAccountById(invalidId));
    }

    @Test
    void getRemovedAccountsTestNonEmptyAccountsSet() {
        Set<AccountDto> expectedBlockedAccounts = new HashSet<>();
        expectedBlockedAccounts.add(new AccountDto());
        Mockito.when(accountService.getBlockedAccounts()).thenReturn(expectedBlockedAccounts);
        try {
            mockMvc.perform(get("/account/get-all-blocked-accounts"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Set<AccountDto> actual = accountController.getRemovedAccounts();
        Assertions.assertEquals(expectedBlockedAccounts, actual);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    void getRemovedAccountsTestEmptyAccountsSet() {
        Set<AccountDto> expectedBlockedAccounts = new HashSet<>();
        Mockito.when(accountService.getBlockedAccounts()).thenReturn(expectedBlockedAccounts);
        Assertions.assertTrue(expectedBlockedAccounts.isEmpty());
    }

    @Test
    void createAccountTestValidRequest() {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();
        accountCreationRequestDto.setClientId(String.valueOf(UUID.randomUUID()));
        accountCreationRequestDto.setAccountType("savings");
        accountCreationRequestDto.setCurrencyCode("USD");
        AccountDto expectedAccountDto = new AccountDto();

        Mockito.when(accountService.createAccount(accountCreationRequestDto)).thenReturn(expectedAccountDto);
        Assertions.assertEquals(expectedAccountDto, accountController.createAccount(accountCreationRequestDto));

        try {
            mockMvc.perform(post("/account/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountCreationRequestDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createAccountTestNotValidRequest() {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();

        try {
            mockMvc.perform(post("/account/create")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountCreationRequestDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void updateAccountStatusTestValidRequest() {
        AccountStatusDto accountStatusDto = new AccountStatusDto();
        accountStatusDto.setAccountStatus("BLOCKED");
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";

        try {
            mockMvc.perform(put("/account/update/" + validUuid)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountStatusDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateAccountStatusTestNotValidRequest() {
        AccountStatusDto accountStatusDto = new AccountStatusDto();
        String uuid = "a00e0000-c00a-0f0b-0f00-0fda0000d000";
        try {
            mockMvc.perform(put("/account/update/" + uuid)
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountStatusDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAccountTestValidRequest() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/account/delete/" + validUuid))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAccountTestNotValidRequest() {
        String invalidUuid = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/account/delete/" + invalidUuid))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAccountsByClientTestValidClient() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        Set<AccountDto> expectedAccounts = new HashSet<>();
        expectedAccounts.add(new AccountDto());
        Mockito.when(accountService.getAccountsByClient(UUID.fromString(validUuid)))
                .thenReturn(expectedAccounts);
        Assertions.assertEquals(expectedAccounts, accountController.getAccountsByClient(validUuid));

        try {
            mockMvc.perform(get("/account/get-all-by-client/" + validUuid))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAccountsByClientTestInvalidClient() {
        String invalidUuid = "a00e0000-c00a-0f0b-0f00-0fda0000d000";
        Set<AccountDto> expectedBlockedAccounts = new HashSet<>();
        Mockito.when(accountService.getAccountsByClient(UUID.fromString(invalidUuid)))
                .thenReturn(expectedBlockedAccounts);
        Assertions.assertEquals(expectedBlockedAccounts, accountController.getAccountsByClient(invalidUuid));

        try {
            mockMvc.perform(get("/account/get-all-by-client/" + invalidUuid))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}