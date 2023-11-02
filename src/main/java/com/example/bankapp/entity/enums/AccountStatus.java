package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ACTIVE(0),
    BLOCKED(1),
    REMOVED(2);

    private final int value;

    AccountStatus(int value){
        this.value = value;
    }
}
