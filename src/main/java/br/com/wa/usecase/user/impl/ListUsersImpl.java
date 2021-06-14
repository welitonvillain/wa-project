package br.com.wa.usecase.user.impl;

import br.com.wa.domain.user.User;
import br.com.wa.http.domain.response.UserListResponse;
import br.com.wa.repository.facade.UserRepositoryFacade;
import br.com.wa.usecase.user.ListUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListUsersImpl implements ListUsers {

  @Autowired
  UserRepositoryFacade userRepository;

  @Override
  public UserListResponse execute() {
    log.info("> Buscando usuários no banco de dados");
    List<User> users = userRepository.findAll();
    log.info("> Encontrados {} usuários.", users.size());

    return new UserListResponse(users);
  }
}
