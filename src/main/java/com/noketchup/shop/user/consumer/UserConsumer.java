package com.noketchup.shop.user.consumer;

import com.noketchup.shop.user.domain.UserDomainModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface UserConsumer {
  void consumeUserDomainEventModel(ConsumerRecord<String, UserDomainModel> userDomainModel);
}
