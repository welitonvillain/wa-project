package br.com.wa.amqp.listener.impl;

import br.com.wa.amqp.domain.KafkaConstants;
import br.com.wa.amqp.domain.UserAmqpMessage;
import br.com.wa.amqp.listener.UserListener;
import br.com.wa.exception.ValidationException;
import br.com.wa.http.domain.request.UserRequest;
import br.com.wa.usecase.user.SaveUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserListenerImpl implements UserListener {

  @Autowired
  private SaveUser saveUser;

  @Override
  @KafkaListener(
      topics = KafkaConstants.TOPIC_REGISTER,
      groupId = KafkaConstants.GROUP_ID,
      containerFactory = KafkaConstants.FACTORY
  )
  public void consumeMessage(UserAmqpMessage message) {
    if (message == null || message.getRegisters().isEmpty()) {
      throw new ValidationException("É necessário informar uma lista com usuários");
    }

    log.info("> Realizando registro de {} usuários.", message.getRegisters().size());
    for (UserRequest user : message.getRegisters()) {
      saveUser.execute(user);
    }
  }
}
