package com.noketchup.shop.user.service;

import com.noketchup.shop.user.controller.dto.UserRequest;
import com.noketchup.shop.user.controller.dto.UserResponse;
import com.noketchup.shop.user.domain.UserDomainModel;
import com.noketchup.shop.user.model.User;
import com.noketchup.shop.user.repository.UserRepository;
import com.noketchup.shop.user.service.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Tag("Service")
class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @Mock
  UserMapper userMapper;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  @DisplayName("Get User")
  void testGetUser() {
    UUID uuid = UUID.randomUUID();
    when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
    when(userMapper.mapToUserResponse(any())).thenReturn(new UserResponse());
    userService.getUser(uuid.toString());
    verify(userRepository, times(1)).findById(any());

    when(userRepository.findById(uuid)).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> userService.getUser(uuid.toString()));
  }

  @Test
  @DisplayName("Get User By Unique Parameters")
  void testGetUserByUniqueParams() {
    when(userRepository.findByEmailOrMobileNumberOrUsername(any(), any(), any())).thenReturn(Optional.of(new User()));
    when(userMapper.mapToUserResponse(any())).thenReturn(new UserResponse());
    UserResponse userResponse = userService.getUserByUniqueParam("", "", "");
    assertNotNull(userResponse);

    when(userRepository.findByEmailOrMobileNumberOrUsername(any(), any(), any())).thenReturn(Optional.empty());
    assertThrows(RuntimeException.class, () -> userService.getUserByUniqueParam("", "", ""));
  }

  @Test
  @DisplayName("Commit Domain")
  void testCommitDomain() {
    when(userRepository.save(any())).thenReturn(new User());
    userService.commitDomain(new UserDomainModel());
    verify(userRepository, times(1)).save(any());
  }

  @DisplayName("Validate User Given Unique values exist")
  @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
  @CsvSource(value = {
          "existingusername,email@email.com,0123456789,15/01/1998",
          "username,existingemail@email.com,0123456789,15/01/1998",
          "username,email@email.com,0123456781,15/01/1998",
  })
  void testWillBlockIfUniqueValuesExistInDb(String username, String email, String phoneNumber, String dateOfBirth) {
    UserRequest userRequest = buildUserRequestFromArgs(username, email, phoneNumber, dateOfBirth);
    BDDMockito
            .given(userRepository.findByEmail("existingemail@email.com"))
            .willReturn(Optional.of(new User()))
            .willReturn(Optional.empty());

    BDDMockito
            .given(userRepository.findByUsername("existingusername"))
            .willReturn(Optional.of(new User()))
            .willReturn(Optional.empty());
    BDDMockito
            .given(userRepository.findByMobileNumber("0123456781"))
            .willReturn(Optional.of(new User()))
            .willReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> userService.validateUser(userRequest));
  }

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