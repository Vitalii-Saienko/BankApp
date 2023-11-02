package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum ManagerStatus {
    ACTIVE(0),
    BLOCKED(1);

    private final int value;

    ManagerStatus(int value){
        this.value = value;
    }
}
