package com.example.bankapp.controller.mvc;

import com.example.bankapp.dto.AccountCreationRequestDto;
import com.example.bankapp.exception.database_exception.DatabaseAccessException;
import com.example.bankapp.service.util.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/web/account")
@RequiredArgsConstructor
public class AccountControllerWeb {
    private final AccountService accountService;

    @GetMapping("/create")
    public String showAccountCreationForm(Model model) {
        model.addAttribute("accountCreationRequestDto", new AccountCreationRequestDto());
        return "account-create";
    }

    @PostMapping(value = "/create", consumes = {"application/x-www-form-urlencoded"})
    public String createAccount(@ModelAttribute("accountCreationRequestDto") @Valid AccountCreationRequestDto accountDTO) {
        try {
            accountService.createAccount(accountDTO);
        } catch (DatabaseAccessException | IllegalArgumentException e) {
            return "redirect:/account-create.html?error=" + e.getMessage();
        }
        return "operation-success";
    }

}
