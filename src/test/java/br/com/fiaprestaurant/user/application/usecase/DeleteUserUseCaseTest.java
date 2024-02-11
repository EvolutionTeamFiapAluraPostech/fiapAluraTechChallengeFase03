package br.com.fiaprestaurant.user.application.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.domain.entity.validator.UuidValidator;
import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import br.com.fiaprestaurant.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private DeleteUserUseCase deleteUserUseCase;

  @Test
  void shouldDeleteAnUser() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findUserByIdRequired(userSchema.getId())).thenReturn(userSchema);

    assertDoesNotThrow(() -> deleteUserUseCase.execute(userSchema.getId().toString()));

    verify(userService).save(userSchema);
  }

  @Test
  void shouldThrowExceptionWhenDeleteAnUserWasNotFound() {
    var user = createUser();
    var userSchema = createUserSchema(user);
    when(userService.findUserByIdRequired(userSchema.getId())).thenThrow(NoResultException.class);

    assertThrows(NoResultException.class,
        () -> deleteUserUseCase.execute(userSchema.getId().toString()));

    verify(userService, never()).save(userSchema);
  }

  @Test
  void shouldThrowExceptionWhenDeleteAnUserUuidIsInvalid() {
    var userUuid = "aaa";
    doThrow(ValidatorException.class).when(uuidValidator).validate(userUuid);

    assertThrows(ValidatorException.class, () -> deleteUserUseCase.execute(userUuid));

    verify(userService, never()).findById(any());
    verify(userService, never()).save(any());
  }
}
