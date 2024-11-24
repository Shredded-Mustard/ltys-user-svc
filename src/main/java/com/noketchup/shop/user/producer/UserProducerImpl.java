package com.noketchup.shop.user.producer;

import com.noketchup.shop.user.domain.UserDomainModel;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProducerImpl implements UserProducer {
  @Value("${config.user.domain.topic}")
  private String userDomainTopic;


  private final KafkaTemplate<String, UserDomainModel> userDomainKafkaTemplate;

  public void sendUserDomainEvent(UserDomainModel userDomainModelObject) {
    ProducerRecord<String, UserDomainModel> record = new ProducerRecord<String, UserDomainModel>(
            userDomainTopic,
            userDomainModelObject.getId().toString(),
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
