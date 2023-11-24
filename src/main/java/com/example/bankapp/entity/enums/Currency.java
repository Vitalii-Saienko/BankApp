package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum Currency {
    UAH(0),
    USD(1),
    EUR(2),
    PLN(4),
    GBP(5),
    CHF(6),
    JPY(7);

    private final int value;

    Currency(int value) {
        this.value = value;
    }
}
