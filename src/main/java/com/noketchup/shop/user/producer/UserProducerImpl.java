package com.noketchup.shop.user.producer;

import com.noketchup.shop.user.domain.model.UserDomainModel;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProducerImpl {
  @Value("${config.user.domain.topic}")
  private final String userDomainTopic;


  private final KafkaTemplate<UUID, UserDomainModel> userDomainKafkaTemplate;

  public void sendUserDomainEvent(UserDomainModel userDomainModelObject) {
    ProducerRecord<UUID, UserDomainModel> record = new ProducerRecord<UUID, UserDomainModel>(
            userDomainTopic,
            userDomainModelObject.getId(),
            userDomainModelObject
    );

    record.headers().add(new DomainEventHeader("NAME", "CREATE_NEW_USER"));

    userDomainKafkaTemplate.send(record);
  }

  public static class DomainEventHeader implements Header {

    private final String key;
    private final String value;

    public DomainEventHeader(String key, String value){
      this.key = key;
      this.value = value;
    }

    @Override
    public String key() {
      return key;
    }

    @Override
    public byte[] value() {
      return value.getBytes();
    }
  }
}
