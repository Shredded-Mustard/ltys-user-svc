package com.noketchup.shop.user.consumer;

import com.mongodb.MongoException;
import com.noketchup.shop.user.domain.UserDomainModel;
import com.noketchup.shop.user.exception.RetryableException;
import com.noketchup.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class UserConsumerImpl implements UserConsumer {
  private final UserService userService;

  @Transactional
  @KafkaListener(topics = "${config.user.domain.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeUserDomainEventModel(ConsumerRecord<String, UserDomainModel> userDomainModel) {
    UserDomainModel userDomainModelObject = userDomainModel.value();
    try {
      userService.commitDomain(userDomainModelObject);
    } catch (MongoException e) {
      throw new RetryableException("Retryable Exception", e.getMessage());
    }

  }
}
