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
public class TransactionCreationRequestDto {
    @NotBlank(message = "Amount cannot be blank") String amount;
    @NotBlank(message = "Description cannot be blank") String description;
    @NotBlank(message = "Debit Account ID cannot be blank") String debitAccountId;
    @NotBlank(message = "Credit Account ID cannot be blank") String creditAccountId;
}
