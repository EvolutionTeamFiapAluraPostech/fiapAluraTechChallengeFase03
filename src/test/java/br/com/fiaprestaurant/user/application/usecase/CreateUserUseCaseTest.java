package br.com.fiaprestaurant.user.application.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserWithId;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  @Spy
  private PasswordEncoder passwordEncoder;
  @Mock
  private UserCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator;
  @InjectMocks
  private CreateUserUseCase createUserUseCase;

  @Test
  void shouldCreateNewUserWhenAllUserAttributesAreCorrect() {
    var user = createNewUser();
    var userToSave = createNewUserWithId();
    when(passwordEncoder.encode(any(String.class))).thenReturn(
        user.getPassword().getPasswordValue());
    when(userService.save(any(User.class))).thenReturn(userToSave);

    var userSaved = createUserUseCase.execute(user);

    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getId()).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(user.getName());
    assertThat(userSaved.getEmail().address()).isEqualTo(user.getEmail().address());
    assertThat(userSaved.getCpf().getCpfNumber()).isEqualTo(user.getCpf().getCpfNumber());
    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail().address());
    verify(userCpfAlreadyRegisteredValidator).validate(user.getCpf().getCpfNumber());
    verify(passwordEncoder).encode(user.getPassword().getPasswordValue());
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = createUser();
    doThrow(DuplicatedException.class).when(userEmailAlreadyRegisteredValidator)
        .validate(user.getEmail().address());

    assertThatThrownBy(() -> createUserUseCase.execute(user)).isInstanceOf(
        DuplicatedException.class);

    verify(userEmailAlreadyRegisteredValidator).validate(user.getEmail().address());
    verify(userService, never()).save(user);
  }
}
