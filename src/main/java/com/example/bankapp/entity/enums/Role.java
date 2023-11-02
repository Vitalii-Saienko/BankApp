package com.example.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    HEAD_MANAGER("HEAD_MANAGER"),
    MANAGER("MANAGER");
    private final String value;
    Role(String value){
        this.value = value;
    }
}
