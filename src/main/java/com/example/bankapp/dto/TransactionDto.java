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
public class TransactionDto {
    @NotBlank(message = "Transaction ID cannot be blank") String transactionId;
    @NotBlank(message = "Debit Account ID cannot be blank") String debitAccountId;
    @NotBlank(message = "Credit Account ID cannot be blank") String creditAccountId;
    @NotBlank(message = "Transaction amount cannot be blank") String amount;
    @NotBlank(message = "Transaction description cannot be blank") String description;
    @NotBlank(message = "Transaction status cannot be blank") String transactionStatus;
    @NotBlank(message = "Transaction createdAt cannot be blank") String createdAt;
}
