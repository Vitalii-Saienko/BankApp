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
public class ProductCreationRequestDto {
    @NotBlank(message = "Manager ID cannot be blank") String managerId;
    @NotBlank(message = "Product Name cannot be blank") String productName;
    @NotBlank(message = "Currency Code cannot be blank") String currencyCode;
    @NotBlank(message = "Interest Rate cannot be blank") String interestRate;
    @NotBlank(message = "Product Limit cannot be blank") String productLimit;
}
