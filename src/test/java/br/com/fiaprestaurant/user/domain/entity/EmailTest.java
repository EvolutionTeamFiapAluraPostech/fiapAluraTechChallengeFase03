package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailTest {

  @Test
  void shouldCreateEmail() {
    assertDoesNotThrow(() -> new Email(DEFAULT_USER_EMAIL));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"email.domain.com", " email.domain.com", "@", "1", "email@domain",
      "A@b@c@example.com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", "email @example.com"})
  void shouldThrowExceptionWhenEmailIsInvalid(String email) {
    assertThrows(ValidatorException.class, () -> new Email(email));
  }

}