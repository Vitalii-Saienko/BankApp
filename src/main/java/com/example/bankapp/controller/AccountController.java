package com.example.bankapp.controller;

import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.service.util.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public AccountDto getAccountById(@PathVariable("id") String id){
        return accountService.getAccountById(UUID.fromString(id));
    }

    @GetMapping("/get-all-blocked-accounts")
    @PreAuthorize("hasRole('MANAGER')")
    public Set<AccountDto> getRemovedAccounts() {
        return accountService.getBlockedAccounts();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MANAGER')")
    public AccountDto createAccount(@RequestBody @Valid AccountCreationRequestDto accountDTO){
        return accountService.createAccount(accountDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public AccountDto updateAccountStatus(@PathVariable("id") String id, @RequestBody @Valid AccountStatusDto accountStatusDto){
        return accountService.updateAccountStatus(UUID.fromString(id), accountStatusDto);
    }

    @DeleteMapping("/delete/{id}")
    public AccountDto deleteAccount(@PathVariable("id") String id){
        return accountService.deleteAccount(UUID.fromString(id));
    }

    @GetMapping("/get-all-by-client/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Set<AccountDto> getAccountsByClient(@PathVariable("id") String id){
        return accountService.getAccountsByClient(UUID.fromString(id));
    }

}
