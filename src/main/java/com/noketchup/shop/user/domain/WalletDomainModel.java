package com.noketchup.shop.user.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletDomainModel {
  private UUID id;
  private String walletNumber;
  private Long availableCredit;
  private String currency;
}
