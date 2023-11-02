package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE(0),
    PENDING(1),
    BLOCKED(2),
    REMOVED(3);

    private final int value;

    Status(int value){
        this.value = value;
    }
}
