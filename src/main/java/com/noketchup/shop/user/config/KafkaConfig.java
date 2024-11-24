package com.noketchup.shop.user.config;

import com.noketchup.shop.user.exception.ConsumeEventFailedException;
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
      //You can configure here what to do when an event fails like sending failure or a rollback event
      throw new ConsumeEventFailedException(consumerRecord, exception);
    }, fixedBackOff);

    //Retryable exceptions will be retried by the number of *maxAttempts* every *interval*
    //Retryable exceptions are Exceptions that may succeed when retrying due to a Mongo Socket exception
    errorHandler.addRetryableExceptions(RetryableException.class);

    //Non Retryable Exceptions are exceptions that may never succeed like exceptions due to validations
    errorHandler.addNotRetryableExceptions(Exception.class);
    return errorHandler;
  }
}
