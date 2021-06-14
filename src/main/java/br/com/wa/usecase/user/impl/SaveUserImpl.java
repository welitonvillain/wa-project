package br.com.wa.usecase.user.impl;

import br.com.wa.domain.converter.UserConverter;
import br.com.wa.domain.user.DataBaseSequence;
import br.com.wa.domain.user.User;
import br.com.wa.exception.ValidationException;
import br.com.wa.http.domain.request.UserRequest;
import br.com.wa.http.domain.response.UserResponse;
import br.com.wa.repository.facade.DataBaseSequenceRepositoryFacade;
import br.com.wa.repository.facade.UserRepositoryFacade;
import br.com.wa.usecase.user.SaveUser;
import br.com.wa.usecase.user.validator.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SaveUserImpl implements SaveUser {

  @Autowired
  UserRepositoryFacade userRepository;

  @Autowired
  DataBaseSequenceRepositoryFacade dataBaseSequenceRepository;

  @Override
  public UserResponse execute(UserRequest request) {
    Validation.validate(request);

    DataBaseSequence sequence = findNextSequence(User.SEQUENCE_NAME);
    User user = UserConverter.convertFromRequest(request, sequence.getSequenceValue());
    log.info("> Registrando usuário na base de dados...");
    userRepository.save(user);
    log.info("> Usuário registrado com sucesso: {}", user.getName());
    return new UserResponse(user);
  }

  @Override
  public DataBaseSequence findNextSequence(String sequenceName) {
    log.info("> Gerando novo idenficador...");
    return dataBaseSequenceRepository.findNextSequence(sequenceName);
  }
}
