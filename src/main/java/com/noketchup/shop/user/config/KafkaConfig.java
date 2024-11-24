package com.noketchup.shop.user.config;

import com.noketchup.shop.user.exception.ConsumeEventFailedException;
import com.noketchup.shop.user.exception.NonRetryableException;
import com.noketchup.shop.user.exception.RetryableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {
  @Value("${spring.kafka.listener.fixed-backoff.interval}")
  private long interval;

  @Value("${spring.kafka.listener.fixed-backoff.max-attempts}")
  private int maxAttempts;

  @Bean
  public DefaultErrorHandler errorHandler() {
    BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
    DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
      throw new ConsumeEventFailedException(consumerRecord, exception);
    }, fixedBackOff);
    errorHandler.addRetryableExceptions(RetryableException.class);
    errorHandler.addNotRetryableExceptions(NonRetryableException.class);
    return errorHandler;
  }
}
