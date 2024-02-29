package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.ALTERNATIVE_USER_CPF;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.user.domain.messages.UserMessages;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserByCpfInteractorTest {

  @Mock
  private UserGateway userService;
  @InjectMocks
  private GetUserByCpfInteractor getUserByCpfInteractor;

  @Test
  void shouldFindUserByCpfWhenUserExists() {
    var user = createUser();
    when(userService.findByCpf(user.getCpf().getCpfNumber())).thenReturn(Optional.of(user));

    var userFound = getUserByCpfInteractor.execute(user.getCpf().getCpfNumber());

    assertThat(userFound).isNotNull();
    assertThat(userFound.getCpf().getCpfNumber()).isEqualTo(user.getCpf().getCpfNumber());
  }

  @Test
  void shouldThrowExceptionWhenUserDoesNotExist() {
    when(userService.findByCpf(ALTERNATIVE_USER_CPF)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getUserByCpfInteractor.execute(ALTERNATIVE_USER_CPF))
        .isInstanceOf(NoResultException.class)
        .hasMessageContaining(UserMessages.USER_CPF_NOT_FOUND.formatted(ALTERNATIVE_USER_CPF));
  }

}