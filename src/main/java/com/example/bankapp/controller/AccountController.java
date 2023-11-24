package com.example.bankapp.controller;

import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.dto.AccountDto;
import com.example.bankapp.dto.AccountStatusDto;
import com.example.bankapp.service.util.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") String id) {
        return ResponseEntity.status(200).body(accountService.getAccountById(UUID.fromString(id)));
    }

    @GetMapping("/get-all-blocked-accounts")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public Set<AccountDto> getRemovedAccounts() {
        return accountService.getBlockedAccounts();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createAccount(@RequestBody @Valid AccountCreationRequestDto accountDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(400).body(bindingResult.getAllErrors());
        }
        AccountDto account = accountService.createAccount(accountDTO);
        return ResponseEntity.status(200).body(account);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public AccountDto updateAccountStatus(@PathVariable("id") String id,
                                          @RequestBody @Valid AccountStatusDto accountStatusDto) {
        return accountService.updateAccountStatus(UUID.fromString(id), accountStatusDto);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public AccountDto deleteAccount(@PathVariable("id") String id) {
        return accountService.deleteAccount(UUID.fromString(id));
    }

    @GetMapping("/get-all-by-client/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public Set<AccountDto> getAccountsByClient(@PathVariable("id") String id) {
        return accountService.getAccountsByClient(UUID.fromString(id));
    }

}
