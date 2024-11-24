package com.noketchup.shop.user.exception;

public class NonRetryableException extends RuntimeException {
  public NonRetryableException(String message) {
    super(message);
  }
}
