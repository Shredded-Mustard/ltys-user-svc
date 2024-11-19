package com.noketchup.shop.user.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDomainModel {
  private UUID id;
  private String username;
  private LocalDate dateOfBirth;
  private String mobileNumber;
  private String email;
  private LocalDate createdAt;
  private LocalDate lastUpdatedAt;
  private String status;
  private List<WalletDomainModel> wallets;
}
