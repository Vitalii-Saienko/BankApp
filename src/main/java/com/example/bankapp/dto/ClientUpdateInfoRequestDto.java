package com.example.bankapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientUpdateInfoRequestDto {
    @NotBlank(message = "Client UUID cannot be blank") String clientId;
    @NotBlank(message = "First Name cannot be blank") String firstName;
    @NotBlank(message = "Last Name cannot be blank") String lastName;
    @Email String email;
    @NotBlank(message = "Address cannot be blank") String address;
    @NotBlank(message = "Phone cannot be blank") String phone;

}
