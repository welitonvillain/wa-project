package br.com.wa.http.impl;

import br.com.wa.domain.user.User;
import br.com.wa.exception.ValidationException;
import br.com.wa.http.URLMapping;
import br.com.wa.http.UserWS;
import br.com.wa.http.domain.request.UserRequest;
import br.com.wa.http.domain.request.UsersRequest;
import br.com.wa.http.domain.response.UserListResponse;
import br.com.wa.http.domain.response.UserResponse;
import br.com.wa.usecase.user.ListUsers;
import br.com.wa.usecase.user.SaveUser;
import br.com.wa.usecase.user.SaveUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserWSImpl implements UserWS {

  @Autowired
  private SaveUser saveUser;

  @Autowired
  private SaveUsers saveUsers;

  @Autowired
  private ListUsers listUsers;

  @Override
  @PostMapping(URLMapping.POST_SAVE_REGISTER)
  public UserResponse saveRegister(@RequestBody UserRequest request) {
    if (request == null) {
      throw new ValidationException("É necessário informar os dados do usuário");
    }

    return saveUser.execute(request);
  }

  @Override
  @PostMapping(URLMapping.POST_SAVE_REGISTERS)
  public UserResponse saveRegisters(@RequestBody UsersRequest request) {
    if (request == null || request.getUsers().isEmpty()) {
      throw new ValidationException("É necessário informar uma lista com usuários");
    }

    saveUsers.execute(request);
    return new UserResponse(null);
  }

  @Override
  @GetMapping(URLMapping.GET_LIST_REGISTER)
  public UserListResponse listRegisters() {
    return listUsers.execute();
  }
}
