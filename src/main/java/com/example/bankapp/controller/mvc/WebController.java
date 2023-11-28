package com.example.bankapp.controller.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class WebController {

    @GetMapping("")
    public String home() {
        return "bankManagerWorkingSpace";
    }

    @GetMapping("/accounts.html")
    public String accountsPage() {
        return "accounts";
    }

    @GetMapping("/agreements.html")
    public String agreementsPage() {
        return "agreements";
    }

    @GetMapping("/clients.html")
    public String clientsPage() {
        return "clients";
    }

    @GetMapping("/managers.html")
    public String managersPage() {
        return "managers";
    }

    @GetMapping("/products.html")
    public String productsPage() {
        return "products";
    }

    @GetMapping("/transactions.html")
    public String transactionsPage() {
        return "transactions";
    }

    @GetMapping("/account-create.html")
    public String accountCreatePage() {
        return "account-create";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
}