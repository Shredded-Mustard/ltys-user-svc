package com.noketchup.shop.user.service;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.UserDomainModel;
import com.noketchup.shop.user.model.User;
import com.noketchup.shop.user.repository.UserRepository;
import com.noketchup.shop.user.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper mapper;
  private static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  private static final Pattern phoneNumberPattern = Pattern.compile("^[0-9]+");

  @Override
  public void validateUser(UserRequest userRequest) {
    //make sure email address is Valid
    if (!emailPattern.matcher(userRequest.getEmail()).matches())
      throw new RuntimeException("Invalid Email Address Provided");
    if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
      throw new RuntimeException("Email already exists");
    }

    //make sure username is Valid
    if (StringUtils.isBlank(userRequest.getUsername()))
      throw new RuntimeException("Username Cannot be an Empty");
    if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
      throw new RuntimeException("Username already exists");
    }

    //make sure date of birth is 18 years or older
    if (userRequest.getDateOfBirth().isAfter(LocalDate.now().minusYears(18))) {
      throw new RuntimeException("User must be 18 years or older");
    }

    //make sure mobile number is 10 digits
    if (!phoneNumberPattern.matcher(userRequest.getMobileNumber()).matches() || userRequest.getMobileNumber().length() != 10) {
      throw new RuntimeException("Mobile number must be 10 digits");
    }
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
