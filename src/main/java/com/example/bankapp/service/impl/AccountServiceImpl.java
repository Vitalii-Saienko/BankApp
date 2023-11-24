package com.example.bankapp.service.impl;

import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Client;
import com.example.bankapp.entity.enums.AccountStatus;
import com.example.bankapp.entity.enums.AccountType;
import com.example.bankapp.entity.enums.Currency;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.mapper.AccountMapper;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.ClientRepository;
import com.example.bankapp.service.util.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientRepository clientRepository;
    private static final String EXCEPTION_MESSAGE_ACC = "Account not found with ID: ";
    private static final String EXCEPTION_MESSAGE_CLIENT = "Client not found with ID: ";

    @Override
    public AccountDto getAccountById(UUID id) {
        return accountMapper.accountToAccountDto(accountRepository.findById(id)
                .orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_ACC + id)));
    }

    @Override
    public Set<AccountDto> getBlockedAccounts() {
        Iterable<Account> all = accountRepository.findAll();
        Set<Account> blockedAccounts = new HashSet<>();
        all.forEach(blockedAccounts::add);
        Set<Account> collect = blockedAccounts.stream()
                .filter(account -> account.getAccountStatus() == AccountStatus.BLOCKED)
                .collect(Collectors.toSet());
        return accountMapper.accountsToAccountDto(collect);
    }

    @Transactional
    @Override
    public AccountDto createAccount(AccountCreationRequestDto creationRequestDto) {
        Account account = new Account();
        Client client =
                clientRepository.findById(UUID.fromString(creationRequestDto.getClientId())).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_CLIENT + creationRequestDto.getClientId()));
        account.setAccountUUID(UUID.randomUUID());
        account.setClientId(client);
        account.setAccountType(AccountType.valueOf(creationRequestDto.getAccountType()));
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountBalance(BigDecimal.valueOf(0.0));
        account.setCurrencyCode(Currency.valueOf(creationRequestDto.getCurrencyCode()));
        account.setCreatedAt(new Timestamp(new Date().getTime()));
        account.setUpdatedAt(new Timestamp(new Date().getTime()));
        account.setCreditTransactionSet(new HashSet<>());
        account.setDebitTransactionSet(new HashSet<>());
        clientRepository.save(client);
        accountRepository.save(account);
        return accountMapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto updateAccountStatus(UUID uuid, AccountStatusDto accountStatusDto) {
        Account account =
                accountRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_ACC + uuid));
        account.setAccountStatus(AccountStatus.valueOf(accountStatusDto.getAccountStatus()));
        accountRepository.save(account);
        return accountMapper.accountToAccountDto(account);
    }

    @Override
    public AccountDto deleteAccount(UUID uuid) {
        Account account =
                accountRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_ACC + uuid));
        account.setAccountStatus(AccountStatus.REMOVED);
        accountRepository.save(account);
        return accountMapper.accountToAccountDto(account);
    }

    @Override
    public Set<AccountDto> getAccountsByClient(UUID uuid) {
        Client client =
                clientRepository.findById(uuid).orElseThrow(() -> new DatabaseAccessException(EXCEPTION_MESSAGE_CLIENT + uuid));
        return accountMapper.accountsToAccountDto(client.getAccountSet());
    }
}
