package com.example.bankapp.service.util;

import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import java.util.Set;
import java.util.UUID;

public interface AccountService {
    AccountDto getAccountById(UUID id);

    Set<AccountDto> getBlockedAccounts();

    AccountDto createAccount(AccountCreationRequestDto creationRequestDto);

    AccountDto updateAccountStatus(UUID uuid, AccountStatusDto accountStatusDto);

    AccountDto deleteAccount(UUID uuid);

    Set<AccountDto> getAccountsByClient(UUID uuid);
}
