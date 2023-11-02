package com.example.bankapp.service.impl;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.entity.enums.AccountStatus;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.mapper.AccountMapper;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAppApplication.class, properties = {"spring.profiles.active=test"})
class AccountServiceImplTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    void getAccountByIdTestCaseValidId() {
        String accountId = "a10e6991-c16a-4f9b-9f04-6fda0510d611";
        String clientId = "672032a7-8cd6-4e17-15b0-78d5aa11b310";
        AccountDto accountById = accountService.getAccountById(UUID.fromString(accountId));
        Assertions.assertNotNull(accountById);
        Assertions.assertEquals(accountById.getClientId(), clientId);
    }

    @Test
    void getAccountByIdTestCaseInvalidId() {
        UUID invalidId = UUID.fromString("a00e0000-c00a-0f0b-0f00-00000000d000");
        Assertions.assertThrows(DatabaseAccessException.class, () -> accountService.getAccountById(invalidId));
    }

    @Test
    void getBlockedAccountsTest() {
        Set<AccountDto> blockedAccounts = accountService.getBlockedAccounts();
        if (!blockedAccounts.isEmpty()) {
            for (AccountDto accountDto : blockedAccounts) {
                Assertions.assertEquals(accountDto.getAccountStatus(), AccountStatus.BLOCKED.name());
            }
        }
    }

    @Transactional
    @Rollback
    @Test
    void createAccountTest() {
        AccountCreationRequestDto accountCreationRequestDto = new AccountCreationRequestDto();
        accountCreationRequestDto.setAccountType("CREDIT");
        accountCreationRequestDto.setCurrencyCode("USD");
        accountCreationRequestDto.setClientId("672032a7-8cd6-4e17-15b0-78d5aa11b310");

        AccountDto account = accountService.createAccount(accountCreationRequestDto);

        Assertions.assertFalse(account.getAccountUUID().isEmpty());
        Assertions.assertEquals("672032a7-8cd6-4e17-15b0-78d5aa11b310", account.getClientId());
    }

    @Transactional
    @Rollback
    @Test
    void updateAccountStatusTest() {
        String accountId = "a10e6991-c16a-4f9b-9f04-6fda0510d612";
        AccountStatusDto accountStatusDto = new AccountStatusDto();
        accountStatusDto.setAccountStatus("BLOCKED");

        AccountDto accountDto = accountService.updateAccountStatus(UUID.fromString(accountId), accountStatusDto);

        Assertions.assertEquals("BLOCKED", accountDto.getAccountStatus());
    }

    @Transactional
    @Rollback
    @Test
    void deleteAccountTest() {
        String accountId = "a10e6991-c16a-4f9b-9f04-6fda8192d61e";
        AccountDto accountDto = accountService.deleteAccount(UUID.fromString(accountId));

        Assertions.assertEquals(accountDto.getAccountStatus(), AccountStatus.REMOVED.name());
    }

    @Transactional
    @Rollback
    @Test
    void getAccountsByClientTest() {
        String clientId = "672032a7-8cd6-4e17-15b0-78d5aa11b310";
        Set<AccountDto> accounts = accountService.getAccountsByClient(UUID.fromString(clientId));
        Assertions.assertFalse(accounts.isEmpty());
    }
}