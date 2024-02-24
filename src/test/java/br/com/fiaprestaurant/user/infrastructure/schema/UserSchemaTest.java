package br.com.fiaprestaurant.user.infrastructure.schema;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiaprestaurant.shared.testData.user.UserTestData;
import org.junit.jupiter.api.Test;

class UserSchemaTest {

  @Test
  void shouldCreateNewUser() {
    var user = UserSchema.builder()
        .name(DEFAULT_USER_NAME)
        .email(UserTestData.DEFAULT_USER_EMAIL)
        .cpf(UserTestData.DEFAULT_USER_CPF)
        .password(UserTestData.DEFAULT_USER_PASSWORD)
        .build();

    assertThat(user).isNotNull();
    assertThat(user.getUsername()).isNotNull().isNotEmpty();
    assertThat(user.isAccountNonExpired()).isTrue();
    assertThat(user.isAccountNonLocked()).isTrue();
    assertThat(user.isCredentialsNonExpired()).isTrue();
    assertThat(user.isEnabled()).isTrue();
  }

  @Test
  void shouldCreateNewUserWithNoArgsConstructor() {
    var user = new UserSchema();
    user.setName(DEFAULT_USER_NAME);

    assertThat(user).isNotNull();
    assertThat(user.getUsername()).isNull();
    assertThat(user.isAccountNonExpired()).isTrue();
    assertThat(user.isAccountNonLocked()).isTrue();
    assertThat(user.isCredentialsNonExpired()).isTrue();
    assertThat(user.isEnabled()).isTrue();
  }

}