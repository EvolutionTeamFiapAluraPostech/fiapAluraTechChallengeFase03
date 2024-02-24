package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.shared.infrastructure.validator.EmailValidator;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailInteractorTest {

  @Mock
  private UserGateway userService;
  @Mock
  private EmailValidator emailValidator;
  @InjectMocks
  private GetUserByEmailInteractor getUserByEmailInteractor;

  @Test
  void shouldGetUserByEmailWhenUserExists() {
    var user = createUser();
    var userEmail = user.getEmail().address();
    when(userService.findByEmail(user.getEmail().address())).thenReturn(Optional.of(user));

    var userFound = getUserByEmailInteractor.execute(userEmail);

    assertThat(userFound).isNotNull();
    assertThat(userFound.getId()).isNotNull().isEqualTo(user.getId());
    verify(emailValidator).validate(userEmail);
  }

  @Test
  void shouldThrowExceptionWhenUserDoesNotExist() {
    var userEmail = DEFAULT_USER_EMAIL;
    when(userService.findByEmail(userEmail)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> getUserByEmailInteractor.execute(userEmail))
        .isInstanceOf(NoResultException.class)
        .hasMessageContaining(USER_EMAIL_NOT_FOUND.formatted(userEmail));

    verify(emailValidator).validate(userEmail);
  }
}
