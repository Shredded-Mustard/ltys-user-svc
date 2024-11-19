package com.noketchup.shop.user.service;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.model.UserDomainModel;

import java.util.UUID;

public interface UserService {
  void validateUser(UserRequest userRequest);
  void commitDomain(UserDomainModel userDomainModelObject);
  UserResponse getUser(UUID id);
}
