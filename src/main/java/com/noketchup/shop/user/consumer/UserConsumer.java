package com.noketchup.shop.user.consumer;

import com.noketchup.shop.user.domain.model.UserDomainModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

import java.util.UUID;

public interface UserConsumer {
  void consumeUserDomainEventModel(ConsumerRecord<UUID, UserDomainModel> userDomainModel);
}
