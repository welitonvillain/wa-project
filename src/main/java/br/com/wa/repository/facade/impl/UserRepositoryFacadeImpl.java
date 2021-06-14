package br.com.wa.repository.facade.impl;

import br.com.wa.domain.user.User;
import br.com.wa.exception.ValidationException;
import br.com.wa.repository.UserRepository;
import br.com.wa.repository.facade.UserRepositoryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryFacadeImpl implements UserRepositoryFacade {

  @Autowired
  UserRepository repository;

  @Override
  public User save(User user) {
    if (repository.findById(user.getId()).isPresent()) {
      throw new ValidationException("Usuário já registrado.");
    }

    return repository.save(user);
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }
}
