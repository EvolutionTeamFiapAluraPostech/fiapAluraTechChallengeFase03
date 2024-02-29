package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.infrastructure.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserSchemaEmailAlreadyRegisteredValidatorTest {

  @Mock
  private UserGateway userService;
  @InjectMocks
  private UserSchemaEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;

  @Test
  void shouldValidateWhenUserEmailDoesNotExist() {
    var user = createUser();
    when(userService.findByEmail(user.getEmail().address())).thenReturn(Optional.empty());

    assertDoesNotThrow(
        () -> userEmailAlreadyRegisteredValidator.validate(user.getEmail().address()));
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = createUser();
    when(userService.findByEmail(user.getEmail().address())).thenReturn(Optional.of(user));

    ThrowingCallable result = () -> userEmailAlreadyRegisteredValidator.validate(
        user.getEmail().address());

    assertThatThrownBy(result).isInstanceOf(DuplicatedException.class);
  }
}
