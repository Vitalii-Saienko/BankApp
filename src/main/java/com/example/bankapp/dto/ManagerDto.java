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
public class ManagerDto {
    @NotBlank(message = "Manager ID cannot be blank") String managerId;
    @NotBlank(message = "First Name cannot be blank") String firstName;
    @NotBlank(message = "Last Name cannot be blank") String lastName;
    @NotBlank(message = "Manager Status cannot be blank") String managerStatus;
    @NotBlank(message = "Created At cannot be blank") String createdAt;
    @NotBlank(message = "Updated At cannot be blank") String updatedAt;
}
