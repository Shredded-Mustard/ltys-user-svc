package com.noketchup.shop.user.consumer;

import com.mongodb.MongoSocketException;
import com.noketchup.shop.user.domain.UserDomainModel;
import com.noketchup.shop.user.exception.RetryableException;
import com.noketchup.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class UserConsumerImpl implements UserConsumer {
  private final UserService userService;

  @Transactional
  @KafkaListener(topics = "${config.user.domain.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeUserDomainEventModel(ConsumerRecord<String, UserDomainModel> userDomainModel, Acknowledgment acknowledgment) {
    UserDomainModel userDomainModelObject = userDomainModel.value();
    try {
      userService.commitDomain(userDomainModelObject);
      acknowledgment.acknowledge();
    }
    catch (MongoSocketException e ){
      acknowledgment.nack(Duration.ofSeconds(1));
      throw new RetryableException("Mongo connection error", e.getMessage());
    }
  }
}
