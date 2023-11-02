package com.example.bankapp.mapper;

import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Client;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import java.util.Set;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
@Component
public interface AccountMapper {
    AccountDto accountToAccountDto(Account account);

    default String map(Client value) {
        return String.valueOf(value.getClientId());
    }

    Set<AccountDto> accountsToAccountDto(Set<Account> accountSet);
}
