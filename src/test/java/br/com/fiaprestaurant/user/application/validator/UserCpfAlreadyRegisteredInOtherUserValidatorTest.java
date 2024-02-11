package br.com.fiaprestaurant.user.application.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.infrastructure.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserCpfAlreadyRegisteredInOtherUserValidatorTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator;

  @Test
  void shouldValidateWhenUserCpfDoesNotExistInOtherUser() {
    var user = createUser();
    when(userService.findByCpf(user.getCpf())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userCpfAlreadyRegisteredInOtherUserValidator.validate(
        user.getId().toString(), user.getCpf()));
  }

  @Test
  void shouldThrowExceptionWhenUserCpfExistsInOtherUser() {
    var user = createUser();
    when(userService.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

    assertThrows(DuplicatedException.class,
        () -> userCpfAlreadyRegisteredInOtherUserValidator.validate(DEFAULT_USER_UUID_FROM_STRING,
            user.getCpf()));
  }

}