package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.domain.service.UserService;
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
  private UserService userService;
  @InjectMocks
  private UserSchemaEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;

  @Test
  void shouldValidateWhenUserEmailDoesNotExist() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByEmail(userSchema.getEmail())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userEmailAlreadyRegisteredValidator.validate(userSchema.getEmail()));
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByEmail(userSchema.getEmail())).thenReturn(Optional.of(userSchema));

    ThrowingCallable result = () -> userEmailAlreadyRegisteredValidator.validate(
        userSchema.getEmail());

    assertThatThrownBy(result).isInstanceOf(DuplicatedException.class);
  }
}
