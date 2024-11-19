package com.noketchup.shop.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserResponse {
  private UUID id;
  private String username;
  private LocalDate dateOfBirth;
  private String mobileNumber;
  private String email;
}
