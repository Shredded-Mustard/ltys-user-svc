package com.noketchup.shop.user.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Wallet {
  private UUID id;
  private String walletNumber;
  private Long availableCredit;
  private String currency;
}
