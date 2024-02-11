package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_CPF;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_NAME;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserTest {

  @Test
  void shouldCreateNewUser() {
    assertDoesNotThrow(() -> new User(DEFAULT_USER_NAME, DEFAULT_USER_EMAIL, DEFAULT_USER_CPF,
        DEFAULT_USER_PASSWORD));
  }

  @Test
  void shouldThrowExceptionWhenUserIdIsNull() {
    assertThrows(ValidatorException.class,
        () -> new User(null, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL, DEFAULT_USER_CPF,
            DEFAULT_USER_PASSWORD));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a"})
  void shouldThrowExceptionWhenUserNameIsInvalid(String name) {
    assertThrows(ValidatorException.class,
        () -> new User(name, DEFAULT_USER_EMAIL, DEFAULT_USER_CPF, DEFAULT_USER_PASSWORD));
  }

  @Test
  void shouldThrowExceptionWhenUserNameMaxLengthIsGreaterThan500Characters() {
    var name = String.join("", Collections.nCopies(501, "a"));
    assertThrows(ValidatorException.class,
        () -> new User(name, DEFAULT_USER_EMAIL, DEFAULT_USER_CPF, DEFAULT_USER_PASSWORD));
  }
}