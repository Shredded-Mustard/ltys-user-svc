package com.noketchup.shop.user.service;

import com.noketchup.shop.user.domain.model.WalletDomainModel;

import java.util.UUID;

public class WalletServiceImpl implements WalletService {
    @Override
    public WalletDomainModel createNewEmptyWalletDomainObject() {
      WalletDomainModel newlyCreatedUserGiftWallet = new WalletDomainModel();
      newlyCreatedUserGiftWallet.setId(UUID.randomUUID());
      newlyCreatedUserGiftWallet.setWalletNumber("A");
      newlyCreatedUserGiftWallet.setAvailableCredit(0L);
      newlyCreatedUserGiftWallet.setCurrency("USD");
      return newlyCreatedUserGiftWallet;
    }
}
