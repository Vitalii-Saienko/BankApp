package com.example.bankapp.controller;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.repository.UserRepository;
import com.example.bankapp.security.UserGenerator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Autowired
    UserGenerator userGenerator;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAccountByIdTestCaseValidId() throws Exception {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        AccountDto accountDto = new AccountDto();
        Mockito.when(accountService.getAccountById(UUID.fromString(validUuid)))
                .thenReturn(accountDto);
        Assertions.assertNotNull(accountController.getAccountById(validUuid));
        Mockito.verify(accountService).getAccountById(UUID.fromString(validUuid));
        mockMvc.perform(get("/account/" + validUuid)
                        .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAccountByIdTestCaseInvalidId() {
        String invalidId = "a00e0000-c00a-0f0b-0f00-00000000d000";
        Mockito.when(accountService.getAccountById(UUID.fromString(invalidId)))
                .thenThrow(DatabaseAccessException.class);
        Assertions.assertThrows(DatabaseAccessException.class, () -> accountController.getAccountById(invalidId));
    }

    @Test
    void getAccountByIdTestCaseSucceedAuthentication() throws Exception {
        String id = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        Mockito.when(accountService.getAccountById(any())).thenReturn(new AccountDto());
        mockMvc.perform(get("/account/" + id)
                        .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountByIdTestCaseForbiddenAuthentication() throws Exception {
        String id = "valid-id-1";
        Mockito.when(accountService.getAccountById(any())).thenReturn(new AccountDto());
        mockMvc.perform(get("/account/" + id)
                        .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getRemovedAccountsTestNonEmptyAccountsSet() {
        Set<AccountDto> expectedBlockedAccounts = new HashSet<>();
        expectedBlockedAccounts.add(new AccountDto());
        Mockito.when(accountService.getBlockedAccounts()).thenReturn(expectedBlockedAccounts);
        Set<AccountDto> actual = accountController.getRemovedAccounts();
        Assertions.assertEquals(expectedBlockedAccounts, actual);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getRemovedAccountsTestEmptyAccountsSet() {
        Set<AccountDto> expectedBlockedAccounts = new HashSet<>();
        Mockito.when(accountService.getBlockedAccounts()).thenReturn(expectedBlockedAccounts);
        Set<AccountDto> actual = accountController.getRemovedAccounts();
        assertTrue(actual.isEmpty() && expectedBlockedAccounts.isEmpty());
    }

    @Test
    void getRemovedAccountsTestNonEmptyAccountsSetAuthorized() {
        try {
            mockMvc.perform(get("/account/get-all-blocked-accounts")
                            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void createAccountTestValidRequest() throws Exception {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();
        accountCreationRequestDto.setClientId(String.valueOf(UUID.randomUUID()));
        accountCreationRequestDto.setAccountType("savings");
        accountCreationRequestDto.setCurrencyCode("USD");

        mockMvc.perform(post("/account/create")
                        .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
                                .contentType(MediaType.APPLICATION_JSON)
                                        .content(new ObjectMapper().writeValueAsString(accountCreationRequestDto)))
                        .andExpect(status().isOk());
    }

    @Test
    void createAccountTestValidRequestAuthorized() throws Exception {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();
        accountCreationRequestDto.setClientId(String.valueOf(UUID.randomUUID()));
        accountCreationRequestDto.setAccountType("savings");
        accountCreationRequestDto.setCurrencyCode("USD");

        mockMvc.perform(post("/account/create")
                        .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
                        .content(new ObjectMapper().writeValueAsString(accountCreationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createAccountTestValidRequestAccessForbidden() throws Exception {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();
        accountCreationRequestDto.setClientId(String.valueOf(UUID.randomUUID()));
        accountCreationRequestDto.setAccountType("savings");
        accountCreationRequestDto.setCurrencyCode("USD");

        mockMvc.perform(post("/account/create")
                        .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN")))
                        .content(new ObjectMapper().writeValueAsString(accountCreationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void createAccountTestNotValidRequest() throws Exception {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();
        accountCreationRequestDto.setClientId("");
        accountCreationRequestDto.setAccountType("");
        accountCreationRequestDto.setCurrencyCode("");

        mockMvc.perform(post("/account/create")
                        .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountCreationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAccountStatusTestValidRequestAuthorized() {
        AccountStatusDto accountStatusDto = new AccountStatusDto();
        accountStatusDto.setAccountStatus("BLOCKED");
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";

        try {
            mockMvc.perform(put("/account/update/" + validUuid)
                            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountStatusDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateAccountStatusTestValidRequestAccessForbidden() {
        AccountStatusDto accountStatusDto = new AccountStatusDto();
        accountStatusDto.setAccountStatus("BLOCKED");
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";

        try {
            mockMvc.perform(put("/account/update/" + validUuid)
                            .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN")))
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountStatusDto)))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateAccountStatusTestNotValidRequest() {
        AccountStatusDto accountStatusDto = new AccountStatusDto();
        String uuid = "a00e0000-c00a-0f0b-0f00-0fda0000d000";
        try {
            mockMvc
                    .perform(put("/account/update/" + uuid)
                            .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN")))
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(accountStatusDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAccountTestValidRequestAuthorized() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/account/delete/" + validUuid)
                    .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteAccountTestValidRequestAccessForbidden() {
        String validUuid = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        try {
            mockMvc.perform(delete("/account/delete/" + validUuid)
                            .with(user("intern").authorities(new SimpleGrantedAuthority("INTERN"))))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void deleteAccountTestNotValidRequest() {
        String invalidUuid = "not-a-valid-uuid";
        try {
            mockMvc.perform(delete("/account/delete/" + invalidUuid)
                    .with(user("admin").authorities(new SimpleGrantedAuthority("ADMIN"))))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
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
    @WithMockUser(authorities = {"ADMIN"})
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