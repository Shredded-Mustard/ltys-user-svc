package com.noketchup.shop.user.model;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "users")
public class User {
  private UUID id;
  @Indexed(unique = true)
  private String username;
  private LocalDate dateOfBirth;
  @Indexed(unique = true)
  private String mobileNumber;
  @Indexed(unique = true)
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdatedAt;
  private String status;
  private List<Wallet> wallets;
}
