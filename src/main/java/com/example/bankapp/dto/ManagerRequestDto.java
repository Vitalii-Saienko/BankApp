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
public class ManagerRequestDto {
        @NotBlank(message = "First Name cannot be blank") String firstName;
        @NotBlank(message = "Last Name cannot be blank") String lastName;
}
