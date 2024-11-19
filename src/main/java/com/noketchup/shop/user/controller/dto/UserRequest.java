package com.noketchup.shop.user.controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
  @NotEmpty
  private String username;
  @NotNull
  private LocalDate dateOfBirth;
  @NotEmpty
  @Pattern(regexp="(^$|[0-9]{10})")
  private String mobileNumber;
  @Email
  private String email;
}
