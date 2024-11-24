package com.noketchup.shop.user.exception;

import org.apache.commons.lang3.StringUtils;

public class RetryableException extends RuntimeException {
  public RetryableException(String ...message) {
    super(StringUtils.join(message, " "));
  }
}
