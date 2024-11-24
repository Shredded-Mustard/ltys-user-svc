package com.noketchup.shop.user.exception;

public class RetryableException extends RuntimeException {
  public RetryableException(String message) {
    super(message);
  }
}
