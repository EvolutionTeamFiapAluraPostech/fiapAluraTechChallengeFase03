package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_VALID_CNPJ;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CnpjTest {

  @Test
  void shouldCreateCnpj() {
    assertDoesNotThrow(() -> new Cnpj(DEFAULT_RESTAURANT_VALID_CNPJ));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345678901234", "11111111111111", "22222222222222",
      "33333333333333", "44444444444444", "55555555555555", "66666666666666", "77777777777777",
      "88888888888888", "99999999999999", "00000000000000"})
  void shouldThrowExceptionWhenCnpjIsInvalid(String cnpj) {
    assertThrows(ValidatorException.class, () -> new Cnpj(cnpj));
  }

}