package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    NEW(0),
    PENDING(1),
    APPROVED(2),
    REJECTED(3);
    private final int value;

    TransactionStatus(int value) {
        this.value = value;
    }
}
