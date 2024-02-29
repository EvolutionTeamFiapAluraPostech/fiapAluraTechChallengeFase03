package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import br.com.fiaprestaurant.shared.infrastructure.validator.UuidValidatorImpl;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteUserInteractorTest {

  @Mock
  private UserGateway userService;
  @Mock
  private UuidValidatorImpl uuidValidator;
  @InjectMocks
  private DeleteUserInteractor deleteUserInteractor;

  @Test
  void shouldDeleteAnUser() {
    var user = createUser();
    when(userService.findUserByIdRequired(user.getId())).thenReturn(user);

    assertDoesNotThrow(() -> deleteUserInteractor.execute(user.getId().toString()));

    verify(userService).delete(user.getId());
  }

  @Test
  void shouldThrowExceptionWhenDeleteAnUserWasNotFound() {
    var userId = UUID.randomUUID();
    when(userService.findUserByIdRequired(userId)).thenThrow(NoResultException.class);

    assertThrows(NoResultException.class, () -> deleteUserInteractor.execute(userId.toString()));
    verify(userService, never()).save(any(User.class));
  }

  @Test
  void shouldThrowExceptionWhenDeleteAnUserUuidIsInvalid() {
    var userUuid = "aaa";
    doThrow(ValidatorException.class).when(uuidValidator).validate(userUuid);

    assertThrows(ValidatorException.class, () -> deleteUserInteractor.execute(userUuid));

    verify(userService, never()).findById(any());
    verify(userService, never()).save(any());
  }
}
