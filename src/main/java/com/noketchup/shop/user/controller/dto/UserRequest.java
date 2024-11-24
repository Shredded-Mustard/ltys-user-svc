package com.noketchup.shop.user.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
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
  @JsonFormat(pattern="yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
  private LocalDate dateOfBirth;
  @NotEmpty
  @Pattern(regexp="^[0-9]+$")
  private String mobileNumber;
  @Email
  private String email;
}
