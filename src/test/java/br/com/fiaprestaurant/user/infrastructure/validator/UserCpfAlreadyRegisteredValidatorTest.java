package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
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
class UserCpfAlreadyRegisteredValidatorTest {

  @Mock
  private UserGateway userService;
  @InjectMocks
  private UserSchemaCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator;

  @Test
  void shouldValidateWhenUserCpfDoesNotExist() {
    var user = createNewUser();
    var userSchema = createNewUserSchema(user);
    when(userService.findByCpf(userSchema.getCpf())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userCpfAlreadyRegisteredValidator.validate(userSchema.getCpf()));
  }

  @Test
  void shouldThrowExceptionWhenUserCpfAlreadyExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByCpf(userSchema.getCpf())).thenReturn(Optional.of(user));

    ThrowingCallable result = () -> userCpfAlreadyRegisteredValidator.validate(userSchema.getCpf());

    assertThatThrownBy(result).isInstanceOf(DuplicatedException.class);
  }

}