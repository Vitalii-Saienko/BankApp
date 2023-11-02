package com.example.bankapp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {
    @NotBlank(message = "Account UUID cannot be blank") String accountUUID;
    @NotBlank(message = "Client ID cannot be blank") String clientId;
    @NotBlank(message = "Account Type cannot be blank") String accountType;
    @NotBlank(message = "Account Status cannot be blank") String accountStatus;
    @NotBlank(message = "Account Balance cannot be blank") String accountBalance;
    @NotBlank(message = "Created At cannot be blank") String createdAt;
    @NotBlank(message = "Updated At cannot be blank") String updatedAt;
    @NotBlank(message = "Currency Code cannot be blank") String currencyCode;
}
