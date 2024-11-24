package com.noketchup.shop.user.controller;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.UserDomainModel;
import com.noketchup.shop.user.domain.WalletDomainModel;
import com.noketchup.shop.user.producer.UserProducer;
import com.noketchup.shop.user.service.UserService;
import com.noketchup.shop.user.service.WalletService;
import com.noketchup.shop.user.service.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

  private final UserService userService;
  private final WalletService walletService;
  private final UserProducer userProducer;
  private final UserMapper mapper;

  @GetMapping("{id}")
  public ResponseEntity<UserResponse> getUserById(
          @PathVariable(value = "id") String id
  ) {
    UserResponse userResponse = userService.getUser(id);
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping
  public ResponseEntity<UserResponse> getUserByUniqueIdentifier(
          @RequestParam(value = "email", required = false) String email,
          @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
          @RequestParam(value = "username", required = false) String username
  ) {
    UserResponse userResponse = userService.getUserByUniqueParam(email, phoneNumber, username);
    return ResponseEntity.ok(userResponse);
  }

  @PostMapping
  public ResponseEntity<String> createNewUser(@RequestBody @Valid UserRequest userRequest) {
    //Validate all fields before taking any action
//    userService.validateUser(userRequest);

    //Map Request to the Domain Model
    UserDomainModel userDomainModelObject = mapper.mapToUserDomain(userRequest);
    WalletDomainModel wallet = walletService.createNewEmptyWalletDomainObject();
    userDomainModelObject.setWallets(List.of(wallet));

    //Send Domain event
    userProducer.sendUserDomainEvent(userDomainModelObject);
    return ResponseEntity.accepted().body("User creation in progress");
  }
}