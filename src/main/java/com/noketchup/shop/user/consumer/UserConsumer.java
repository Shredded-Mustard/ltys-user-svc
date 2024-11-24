package com.noketchup.shop.user.consumer;

import com.noketchup.shop.user.domain.UserDomainModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface UserConsumer {
  void consumeUserDomainEventModel(ConsumerRecord<String, UserDomainModel> userDomainModel, Acknowledgment acknowledgment);
}
