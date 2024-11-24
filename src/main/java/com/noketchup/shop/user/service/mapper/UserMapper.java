package com.noketchup.shop.user.service.mapper;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.UserDomainModel;
import com.noketchup.shop.user.domain.WalletDomainModel;
import com.noketchup.shop.user.model.User;
import com.noketchup.shop.user.model.Wallet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserMapper {

  public UserDomainModel mapToUserDomain(UserRequest userRequest) {
    UserDomainModel userDomainModel = new UserDomainModel();
    userDomainModel.setId(UUID.randomUUID());
    userDomainModel.setUsername(userRequest.getUsername());
    userDomainModel.setDateOfBirth(userRequest.getDateOfBirth());
    userDomainModel.setEmail(userRequest.getEmail());
    userDomainModel.setMobileNumber(userRequest.getMobileNumber());
    userDomainModel.setCreatedAt(LocalDateTime.now());
    userDomainModel.setLastUpdatedAt(LocalDateTime.now());
    userDomainModel.setStatus("PENDING_ACTIVATION");
    return userDomainModel;
  }

  public User mapToUser(UserDomainModel userDomainModelObject) {
    User user = new User();
    user.setId(userDomainModelObject.getId());
    user.setUsername(userDomainModelObject.getUsername());
    user.setDateOfBirth(userDomainModelObject.getDateOfBirth());
    user.setCreatedAt(userDomainModelObject.getCreatedAt());
    user.setLastUpdatedAt(userDomainModelObject.getLastUpdatedAt());
    user.setEmail(userDomainModelObject.getEmail());
    user.setMobileNumber(userDomainModelObject.getMobileNumber());
    user.setStatus(userDomainModelObject.getStatus());
    user.setWallets(mapToWallets(userDomainModelObject.getWallets()));
    return user;
  }

  public List<Wallet> mapToWallets(List<WalletDomainModel> wallets) {
    return wallets
            .stream()
            .map(walletDomainModel -> {
              Wallet wallet = new Wallet();
              wallet.setId(walletDomainModel.getId());
              wallet.setAvailableCredit(walletDomainModel.getAvailableCredit());
              wallet.setCurrency(walletDomainModel.getCurrency());
              wallet.setWalletNumber(walletDomainModel.getWalletNumber());
              return wallet;
            }).toList();

  }

  public UserResponse mapToUserResponse(User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setEmail(user.getEmail());
    userResponse.setUsername(user.getUsername());
    userResponse.setDateOfBirth(user.getDateOfBirth());
    userResponse.setMobileNumber(user.getMobileNumber());
    return userResponse;
  }
}
