package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_CPF;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;

class UserBuilderTest {

  @Test
  void shouldCreateNewUserWithId() {
    var user = new UserBuilder().setId(DEFAULT_USER_UUID)
        .setName(DEFAULT_USER_NAME)
        .setEmail(DEFAULT_USER_EMAIL)
        .setCpf(DEFAULT_USER_CPF)
        .setPassword(DEFAULT_USER_PASSWORD)
        .createUser();

    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull();
  }

  @Test
  void shouldCreateUserToUpdate() {
    var user = new UserBuilder()
        .setName(DEFAULT_USER_NAME)
        .setEmail(DEFAULT_USER_EMAIL)
        .setCpf(DEFAULT_USER_CPF)
        .updateUser();

    assertThat(user).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenCreateUserWithoutName() {
    assertThatThrownBy(() -> new UserBuilder()
        .setEmail(DEFAULT_USER_EMAIL)
        .setCpf(DEFAULT_USER_CPF)
        .createUser())
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowExceptionWhenCreateUserWithoutEmail() {
    assertThatThrownBy(() -> new UserBuilder()
        .setName(DEFAULT_USER_NAME)
        .setCpf(DEFAULT_USER_CPF)
        .createUser())
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowExceptionWhenCreateUserWithoutCpf() {
    assertThatThrownBy(() -> new UserBuilder()
        .setName(DEFAULT_USER_NAME)
        .setEmail(DEFAULT_USER_EMAIL)
        .createUser())
        .isInstanceOf(ValidatorException.class);
  }

}