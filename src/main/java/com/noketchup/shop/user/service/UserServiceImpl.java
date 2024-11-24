package com.noketchup.shop.user.service;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.model.UserDomainModel;
import com.noketchup.shop.user.model.User;
import com.noketchup.shop.user.repository.UserRepository;
import com.noketchup.shop.user.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper mapper;

  @Override
  public void validateUser(UserRequest userRequest) {
    //make sure email address and username is unique
    if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
      throw new RuntimeException("Email already exists");
    }

    if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
      throw new RuntimeException("Username already exists");
    }

    //make sure date of birth is 18 years or older
    if (userRequest.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
      throw new RuntimeException("User must be 18 years or older");
    }

    //make sure mobile number is 10 digits
//    if(userRequest.getMobileNumber().length() != 10) {
//      throw new RuntimeException("Mobile number must be 10 digits");
//    }
  }

  @Override
  public void commitDomain(UserDomainModel userDomainModelObject) {
    User user = mapper.mapToUser(userDomainModelObject);
    userRepository.save(user);
  }


  @Override
  public UserResponse getUser(String id) {
    User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("User not found"));
    return mapper.mapToUserResponse(user);
  }

  @Override
  public UserResponse getUserByUniqueParam(String email, String phoneNumber, String username) {
    User user = userRepository.findByEmailOrMobileNumberOrUsername(email, phoneNumber, username)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return mapper.mapToUserResponse(user);

  }

}
