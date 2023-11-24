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
public class AccountCreationRequestDto {

    @NotBlank(message = "Client ID cannot be blank")
    private String clientId;
    @NotBlank(message = "Account Type cannot be blank")
    private String accountType;
    @NotBlank(message = "Currency Code cannot be blank")
    private String currencyCode;

}
