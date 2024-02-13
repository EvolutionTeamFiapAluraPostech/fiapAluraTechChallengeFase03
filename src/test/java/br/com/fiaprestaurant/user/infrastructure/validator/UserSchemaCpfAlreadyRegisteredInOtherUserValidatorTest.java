package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createAlternativeUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
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
    when(userService.findByCpf(user.getCpf().getCpfNumber())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userCpfAlreadyRegisteredInOtherUserValidator.validate(
        user.getId().toString(), user.getCpf().getCpfNumber()));
  }

  @Test
  void shouldThrowExceptionWhenUserCpfExistsInOtherUser() {
    var user = createUser();
    var otherUser = createAlternativeUser();
    when(userService.findByCpf(user.getCpf().getCpfNumber())).thenReturn(Optional.of(otherUser));

    ThrowingCallable result = () -> userCpfAlreadyRegisteredInOtherUserValidator.validate(
        DEFAULT_USER_UUID_FROM_STRING, user.getCpf().getCpfNumber());

    assertThatThrownBy(result).isInstanceOf(DuplicatedException.class);
  }

}