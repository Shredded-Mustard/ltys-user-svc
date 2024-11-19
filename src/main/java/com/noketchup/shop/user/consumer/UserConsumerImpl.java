package com.noketchup.shop.user.consumer;

import com.noketchup.shop.user.domain.model.UserDomainModel;
import com.noketchup.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserConsumerImpl implements UserConsumer {

  private final UserService userService;

  @Transactional
  @KafkaListener(topics = "${config.user.domain.topic}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeUserDomainEventModel(ConsumerRecord<UUID, UserDomainModel> userDomainModel) {
    UserDomainModel userDomainModelObject = userDomainModel.value();
    userService.commitDomain(userDomainModelObject);
  }
}
