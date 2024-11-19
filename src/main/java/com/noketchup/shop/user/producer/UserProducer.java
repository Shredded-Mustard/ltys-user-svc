package com.noketchup.shop.user.producer;


import com.noketchup.shop.user.domain.model.UserDomainModel;

public interface UserProducer {
  void sendUserDomainEvent(UserDomainModel userDomainModelObject);
}
