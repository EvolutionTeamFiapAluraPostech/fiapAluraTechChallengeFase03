package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
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
class UserSchemaEmailAlreadyRegisteredInOtherUserValidatorTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserSchemaEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;

  @Test
  void shouldValidateWhenUserEmailDoesNotExistInOtherEmail() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByEmail(userSchema.getEmail())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userEmailAlreadyRegisteredInOtherUserValidator.validate(
        userSchema.getId().toString(), userSchema.getEmail()));
  }

  @Test
  void shouldThrowExceptionWhenUserEmailExistsInOtherEmail() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByEmail(userSchema.getEmail())).thenReturn(Optional.of(userSchema));

    ThrowingCallable result = () -> userEmailAlreadyRegisteredInOtherUserValidator.validate(
        DEFAULT_USER_UUID_FROM_STRING, userSchema.getEmail());

    assertThatThrownBy(result).isInstanceOf(DuplicatedException.class);
  }
}
