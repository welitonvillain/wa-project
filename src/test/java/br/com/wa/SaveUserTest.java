package br.com.wa;

import br.com.wa.domain.user.DataBaseSequence;
import br.com.wa.domain.user.User;
import br.com.wa.exception.ValidationException;
import br.com.wa.http.domain.request.AddressRequest;
import br.com.wa.http.domain.request.UserRequest;
import br.com.wa.http.domain.response.UserResponse;
import br.com.wa.repository.UserRepository;
import br.com.wa.repository.facade.DataBaseSequenceRepositoryFacade;
import br.com.wa.repository.facade.impl.UserRepositoryFacadeImpl;
import br.com.wa.usecase.user.SaveUser;
import br.com.wa.usecase.user.impl.SaveUserImpl;
import br.com.wa.usecase.user.validator.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SaveUserTest {

  @InjectMocks
  private SaveUserImpl saveUser;

  @Mock
  private UserRepositoryFacadeImpl userRepository;

  @Mock
  DataBaseSequenceRepositoryFacade dataBaseSequenceRepository;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("Register a new user")
  @Test
  void register_user() {
    User user = new User();
    user.setId(1);
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserRequest request = new UserRequest();
    request.setName("Joao");
    request.setAge(20);
    request.setEmail("joao@email.com");
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("rua um");
    addressRequest.setPostalCode("88888-000");
    request.setAddress(addressRequest);

    DataBaseSequence sequence = new DataBaseSequence();
    sequence.setSequenceValue(1);
    when(saveUser.findNextSequence(anyString())).thenReturn(sequence);

    UserResponse response = saveUser.execute(request);
    Assertions.assertEquals(user.getId(), response.getUser().getId());
  }

  @Test
  void validate_invalid_postal_code() {
    String postalCode = "88818000";
    Assertions.assertThrows(ValidationException.class, () -> Validation.validatePostalCode(postalCode));
  }

  @Test
  void validate_valid_postal_code() {
    String postalCode = "88818-000";
    Assertions.assertDoesNotThrow(() -> Validation.validatePostalCode(postalCode));
  }

  @Test
  void validate_invalid_email() {
    String email = "email.com";
    Assertions.assertThrows(ValidationException.class, () -> Validation.validateEmail(email));
  }

  @Test
  void validate_valid_email() {
    String email = "email@email.com";
    Assertions.assertDoesNotThrow(() -> Validation.validateEmail(email));
  }

}
