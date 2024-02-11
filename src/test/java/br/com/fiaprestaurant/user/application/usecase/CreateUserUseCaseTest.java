package br.com.fiaprestaurant.user.application.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.domain.service.UserService;
import java.util.UUID;
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
    var user = createUser();
    var userSchema = createNewUserSchema(user);
    var userSchemaSaved = createNewUserSchema(user);
    userSchemaSaved.setId(UUID.randomUUID());
    when(passwordEncoder.encode(userSchema.getPassword())).thenReturn(userSchema.getPassword());
    when(userService.save(userSchema)).thenReturn(userSchemaSaved);

    var userSaved = createUserUseCase.execute(user);

    assertThat(userSaved).isNotNull();
    assertThat(userSaved.getId()).isNotNull();
    assertThat(userSaved.getName()).isEqualTo(userSchema.getName());
    assertThat(userSaved.getEmail().address()).isEqualTo(userSchema.getEmail());
    assertThat(userSaved.getCpf().getCpf()).isEqualTo(userSchema.getCpf());
    verify(userEmailAlreadyRegisteredValidator).validate(userSchema.getEmail());
    verify(userCpfAlreadyRegisteredValidator).validate(userSchema.getCpf());
    verify(passwordEncoder).encode(userSchema.getPassword());
    verify(userService).save(userSchema);
  }

  @Test
  void shouldThrowExceptionWhenUserAlreadyExists() {
    var user = createUser();
    var userSchema = createNewUserSchema(user);
    doThrow(DuplicatedException.class).when(userEmailAlreadyRegisteredValidator)
        .validate(userSchema.getEmail());

    assertThatThrownBy(() -> createUserUseCase.execute(user)).isInstanceOf(
        DuplicatedException.class);

    verify(userEmailAlreadyRegisteredValidator).validate(userSchema.getEmail());
    verify(userService, never()).save(userSchema);
  }
}
