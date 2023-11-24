package com.example.bankapp.entity.enums;


import lombok.Getter;


@Getter
public enum Role {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    INTERN("INTERN");
    private final String value;

    Role(String value) {
        this.value = value;
    }
}
