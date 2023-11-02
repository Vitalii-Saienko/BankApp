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
public class ClientDto {
    @NotBlank(message = "Client UUID cannot be blank") String clientId;
    @NotBlank(message = "Client Status cannot be blank") String clientStatus;
    @NotBlank(message = "Tax Code cannot be blank") String taxCode;
    @NotBlank(message = "First Name cannot be blank") String firstName;
    @NotBlank(message = "Last Name cannot be blank") String lastName;
    @NotBlank(message = "Email cannot be blank") String email;
    @NotBlank(message = "Address cannot be blank") String address;
    @NotBlank(message = "Phone cannot be blank") String phone;
    @NotBlank(message = "Created At cannot be blank") String createdAt;
    @NotBlank(message = "Updated At cannot be blank") String updatedAt;
}
