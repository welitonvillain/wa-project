package br.com.wa.usecase.user.validator;

import br.com.wa.exception.ValidationException;
import br.com.wa.http.domain.request.UserRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validation {
  private static final String POSTAL_CODE_REGEX = "^\\d{5}(-\\d{3})?$";
  private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

  public static void validatePostalCode(String postalCode) {
    if(!postalCode.matches(POSTAL_CODE_REGEX)) {
      throw new ValidationException("O código postal deve obedecer o formato XXXXX-XXX");
    }
  }

  public static void validateEmail(String email) {
    if (!email.matches(EMAIL_REGEX)) {
      throw new ValidationException("Email inválido");
    }
  }

  public static void validate(UserRequest request) {
    log.info("> Iniciando processo de validação dos dados.");
    if (request == null) throw new ValidationException("Um usuário precisa ser informado.");

    if (request.getAddress() != null && request.getAddress().getPostalCode() != null)
      validatePostalCode(request.getAddress().getPostalCode());

    if (request.getEmail() != null)
      validateEmail(request.getEmail());
  }
}
