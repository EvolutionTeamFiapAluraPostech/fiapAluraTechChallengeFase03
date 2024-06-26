package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.shared.infrastructure.validator.UuidValidatorImpl;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserByIdInteractorTest {

  @Mock
  private UserGateway userService;
  @Mock
  private UuidValidatorImpl uuidValidator;
  @InjectMocks
  private GetUserByIdInteractor getUserByIdInteractor;

  @Test
  void shouldFindUserByIdWhenUserExists() {
    var user = createUser();
    var userUuid = user.getId().toString();
    when(userService.findById(UUID.fromString(userUuid))).thenReturn(Optional.of(user));

    var userFound = getUserByIdInteractor.execute(userUuid);

    assertThat(userFound).isNotNull();
    verify(uuidValidator).validate(userUuid);
  }

  @Test
  void shouldThrowExceptionWhenUserDoesNotExist() {
    var userUuid = UUID.randomUUID();
    when(userService.findById(userUuid)).thenReturn(Optional.empty());

    assertThrows(NoResultException.class, () -> getUserByIdInteractor.execute(userUuid.toString()));

    verify(uuidValidator).validate(userUuid.toString());
  }
}
