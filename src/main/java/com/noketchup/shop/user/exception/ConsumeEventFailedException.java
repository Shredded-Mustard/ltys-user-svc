package com.noketchup.shop.user.exception;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumeEventFailedException extends RuntimeException {
  public ConsumeEventFailedException(ConsumerRecord<?,?> consumerRecord, Throwable cause) {
    super(cause);
  }
}
