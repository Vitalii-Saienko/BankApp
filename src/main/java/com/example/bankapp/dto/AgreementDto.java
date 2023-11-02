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
public class AgreementDto {
    @NotBlank(message = "Agreement ID cannot be blank") String agreementId;
    @NotBlank(message = "Account ID cannot be blank") String accountId;
    @NotBlank(message = "Product ID cannot be blank") String productId;
    @NotBlank(message = "Interest Rate cannot be blank") String interestRate;
    @NotBlank(message = "Agreement Status cannot be blank") String agreementStatus;
    @NotBlank(message = "Agreement Sum cannot be blank") String agreementSum;
    @NotBlank(message = "Created At cannot be blank") String createdAt;
}
