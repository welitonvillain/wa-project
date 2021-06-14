package br.com.wa.usecase.user.impl;

import br.com.wa.amqp.domain.KafkaConstants;
import br.com.wa.amqp.domain.UserAmqpMessage;
import br.com.wa.domain.user.User;
import br.com.wa.http.domain.request.UserRequest;
import br.com.wa.http.domain.request.UsersRequest;
import br.com.wa.http.domain.response.UserResponse;
import br.com.wa.usecase.user.SaveUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class SaveUsersImpl implements SaveUsers {

  @Autowired
  private KafkaTemplate<String, UserAmqpMessage> registerKafkaTemplate;

  @Override
  @Async
  public UserResponse execute(UsersRequest request) {
    ListenableFuture<SendResult<String, UserAmqpMessage>> future = registerKafkaTemplate.send(
        KafkaConstants.TOPIC_REGISTER,
        new UserAmqpMessage(request.getUsers())
    );

    future.addCallback(new ListenableFutureCallback<SendResult<String, UserAmqpMessage>>() {
      @Override
      public void onFailure(Throwable throwable) {
        log.error("> Ocorreu um erro ao enviar mensagem: " + throwable.getMessage());
        throw new RuntimeException("Não foi possível realizar o registro de usuários.");
      }

      @Override
      public void onSuccess(SendResult<String, UserAmqpMessage> stringUserAmqpMessageSendResult) {
        log.info("> Mensagem enviada ao Tópico com sucesso. \n Info: {}",
            stringUserAmqpMessageSendResult.getProducerRecord().value()
        );
      }
    });

    return null;
  }
}
