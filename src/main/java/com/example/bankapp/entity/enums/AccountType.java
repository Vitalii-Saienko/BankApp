package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    CREDIT(0),
    DEPOSIT(1),
    CURRENT(2),
    BUSINESS(3),
    SALARY(4),
    PENSION(5);

    private final int value;

    AccountType(int value){
        this.value = value;
    }

}
