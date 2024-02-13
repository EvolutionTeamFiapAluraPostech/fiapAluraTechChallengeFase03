package br.com.fiaprestaurant.user.application.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_CPF;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.shared.infrastructure.validator.UuidValidatorImpl;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

  @Mock
  private UserService userService;
  @Mock
  private UuidValidatorImpl uuidValidator;
  @Mock
  private UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;
  @Mock
  private UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator;
  @InjectMocks
  private UpdateUserUseCase updateUserUseCase;

  @Test
  void shouldUpdateUser() {
    var user = createUser();
    var userToUpdate = new User(DEFAULT_USER_UUID, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL,
        DEFAULT_USER_CPF, DEFAULT_USER_PASSWORD);
    when(userService.findUserByIdRequired(userToUpdate.getId())).thenReturn(userToUpdate);
    when(userService.update(user.getId(), userToUpdate)).thenReturn(userToUpdate);

    var userUpdated = updateUserUseCase.execute(userToUpdate.getId().toString(), userToUpdate);

    assertThat(userUpdated).isNotNull();
    assertThat(userUpdated.getId()).isNotNull().isEqualTo(userToUpdate.getId());
    verify(uuidValidator).validate(userToUpdate.getId().toString());
    verify(userEmailAlreadyRegisteredInOtherUserValidator)
        .validate(userToUpdate.getId().toString(), userToUpdate.getEmail().address());
    verify(userCpfAlreadyRegisteredInOtherUserValidator)
        .validate(userToUpdate.getId().toString(), userToUpdate.getCpf().getCpfNumber());
  }
}
