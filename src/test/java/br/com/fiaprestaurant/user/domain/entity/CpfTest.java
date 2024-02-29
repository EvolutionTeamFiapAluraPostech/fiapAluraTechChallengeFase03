package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_CPF;
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
class CpfTest {

  @Test
  void shouldAcceptCpfInformed() {
    assertDoesNotThrow(() -> new Cpf(DEFAULT_USER_CPF));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "123", "12345678901", "11111111111", "22222222222", "33333333333",
      "44444444444", "55555555555", "66666666666", "77777777777", "88888888888", "99999999999",
      "00000000000"})
  void shouldThrowExceptionWhenCpfIsInvalid(String cpfValue) {
    assertThrows(ValidatorException.class, () -> new Cpf(cpfValue));
  }

}