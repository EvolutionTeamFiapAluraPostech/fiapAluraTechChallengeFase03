package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createAlternativeUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.domain.service.UserService;
import java.util.Optional;
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
    when(userService.findByEmail(user.getEmail().address())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> userEmailAlreadyRegisteredInOtherUserValidator.validate(
        user.getId().toString(), user.getEmail().address()));
  }

  @Test
  void shouldThrowExceptionWhenUserEmailExistsInOtherEmail() {
    var user = createUser();
    var otherUser = createAlternativeUser();
    when(userService.findByEmail(user.getEmail().address())).thenReturn(Optional.of(otherUser));

    assertThrows(DuplicatedException.class,
        () -> userEmailAlreadyRegisteredInOtherUserValidator.validate(DEFAULT_USER_UUID_FROM_STRING,
            user.getEmail().address()));
  }
}
