package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.infrastructure.service.UserSchemaService;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserSchemaCpfAlreadyRegisteredInOtherUserValidatorTest {

  @Mock
  private UserSchemaService userService;
  @InjectMocks
  private UserSchemaCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator;

  @Test
  void shouldValidateWhenUserCpfDoesNotExistInOtherUser() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByCpf(userSchema.getCpf())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userCpfAlreadyRegisteredInOtherUserValidator.validate(
        userSchema.getId().toString(), userSchema.getCpf()));
  }

  @Test
  void shouldThrowExceptionWhenUserCpfExistsInOtherUser() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findByCpf(userSchema.getCpf())).thenReturn(Optional.of(userSchema));

    ThrowingCallable result = () -> userCpfAlreadyRegisteredInOtherUserValidator.validate(
        DEFAULT_USER_UUID_FROM_STRING, userSchema.getCpf());

    assertThatThrownBy(result).isInstanceOf(DuplicatedException.class);
  }

}