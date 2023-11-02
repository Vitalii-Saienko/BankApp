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
public class AgreementCreationRequestDto {

    @NotBlank(message = "Product ID cannot be blank") String productId;
    @NotBlank(message = "Account ID cannot be blank") String accountId;
    @NotBlank(message = "Agreement sum cannot be blank") String agreementSum;
}
