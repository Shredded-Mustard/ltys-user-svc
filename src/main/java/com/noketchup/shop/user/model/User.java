package com.noketchup.shop.user.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "users")
public class User {
  private UUID id;
  private String username;
  private LocalDate dateOfBirth;
  private String mobileNumber;
  private String email;
  private LocalDate createdAt;
  private LocalDate lastUpdatedAt;
  private String status;
  private List<Wallet> wallets;
}
