package com.noketchup.shop.user.controller;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.repository.UserRepository;
import com.noketchup.shop.user.service.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;

  @DisplayName("Validate User Request")
  @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
  @CsvFileSource(resources = "/User Validation Test Cases.csv", numLinesToSkip = 1)
  void userValidationTest(String username, String email, String mobileNumber, String dateOfBirth, Boolean isValid, String errorMessage) {
    passAllValidationRepoCalls();
    UserRequest userRequest = buildUserRequestFromArgs(username, email, mobileNumber, dateOfBirth);
    if (!isValid) {
      RuntimeException exc = assertThrows(RuntimeException.class, () -> userService.validateUser(userRequest));
      assertEquals(exc.getMessage(), errorMessage);
    } else
      assertDoesNotThrow(() -> userService.validateUser(userRequest));
  }

  private UserRequest buildUserRequestFromArgs(String username, String email, String mobileNumber, String dateOfBirth) {
    UserRequest userRequest = new UserRequest();
    userRequest.setUsername(Objects.equals(username, "empty") ? "" : username);
    userRequest.setEmail(email);
    userRequest.setMobileNumber(StringUtils.replace(mobileNumber, "text:", ""));
    userRequest.setDateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    return userRequest;
  }

  private void passAllValidationRepoCalls() {
    when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
    when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
  }
}