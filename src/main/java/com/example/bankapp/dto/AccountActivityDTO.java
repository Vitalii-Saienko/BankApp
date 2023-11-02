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
public class AccountActivityDTO {
    @NotBlank(message = "Year cannot be blank" ) String year;
    @NotBlank(message = "Month cannot be blank") String month;
}
