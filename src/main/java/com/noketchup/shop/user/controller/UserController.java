package com.noketchup.shop.user.controller;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.model.UserDomainModel;
import com.noketchup.shop.user.domain.model.WalletDomainModel;
import com.noketchup.shop.user.producer.UserProducer;
import com.noketchup.shop.user.service.UserService;
import com.noketchup.shop.user.service.WalletService;
import com.noketchup.shop.user.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final WalletService walletService;
  private final UserProducer userProducer;
  private final UserMapper mapper;

  @GetMapping("{id}")
  public ResponseEntity<UserResponse> getUserByIdentifier(
          @PathVariable(value = "id") UUID id
  ) {
    UserResponse userResponse = userService.getUser(id);
    return ResponseEntity.ok(userResponse);
  }

  @PostMapping
  public ResponseEntity<String> createNewUser(@RequestBody UserRequest userRequest) {
    userService.validateUser(userRequest);

    UserDomainModel userDomainModelObject = mapper.mapToUserDomain(userRequest);
    WalletDomainModel wallet = walletService.createNewEmptyWalletDomainObject();
    userDomainModelObject.setWallets(List.of(wallet));

    userProducer.sendUserDomainEvent(userDomainModelObject);
    return ResponseEntity.accepted().body("User creation in progress");
  }
}