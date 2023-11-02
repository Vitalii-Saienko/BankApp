package com.example.bankapp.mapper;

import com.example.bankapp.BankAppApplication;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Client;
import com.example.bankapp.entity.enums.AccountStatus;
import com.example.bankapp.entity.enums.AccountType;
import com.example.bankapp.entity.enums.Currency;
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
class AccountMapperTest {

    @Autowired
    AccountMapper accountMapper;

    @Test
    void accountToAccountDtoTest() {
        Account account = new Account();
        account.setAccountBalance(BigDecimal.valueOf(1000));
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountType(AccountType.CREDIT);
        account.setCurrencyCode(Currency.EUR);
        account.setAccountUUID(UUID.fromString("2a4ff07-2cf9-4c7b-8e15-9e2dcb67fe8b"));
        account.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setDebitTransactionSet(new HashSet<>());
        account.setCreditTransactionSet(new HashSet<>());
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setClientId(new Client());
        account.setAgreementId(new HashSet<>());

        AccountDto accountDto = accountMapper.accountToAccountDto(account);

        Assertions.assertNotNull(accountDto);
        Assertions.assertEquals(account.getAccountBalance().toString(), accountDto.getAccountBalance());
    }

    @Test
    void accountsToAccountDtoTest() {
        Set<Account> accountSet = new HashSet<>();
        Account account = new Account();
        account.setAccountBalance(BigDecimal.valueOf(1000));
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountType(AccountType.CREDIT);
        account.setCurrencyCode(Currency.EUR);
        account.setAccountUUID(UUID.fromString("2a4ff07-2cf9-4c7b-8e15-9e2dcb67fe8b"));
        account.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setDebitTransactionSet(new HashSet<>());
        account.setCreditTransactionSet(new HashSet<>());
        account.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setClientId(new Client());
        account.setAgreementId(new HashSet<>());
        accountSet.add(account);

        Set<AccountDto> dtoSet = accountMapper.accountsToAccountDto(accountSet);
        Assertions.assertEquals(1, dtoSet.size());
    }
}