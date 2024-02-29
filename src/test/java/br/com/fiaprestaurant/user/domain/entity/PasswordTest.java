package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordTest {

  @Test
  void shouldAcceptPassword() {
    assertDoesNotThrow(() -> new Password(DEFAULT_USER_PASSWORD));
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "1", "B", "@", "abcdefghijk", "0ABCDEFGHI", "abcd1234", "Abcd1234",
      "@#$%^&+=ab1"})
  void shouldThrowExceptionWhenCpfIsInvalid(String passwordValue) {
    assertThrows(ValidatorException.class, () -> new Password(passwordValue));
  }
}