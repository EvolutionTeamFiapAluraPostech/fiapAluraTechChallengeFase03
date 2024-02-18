package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CoordinatesTest {

  @Test
  void shouldCreateCoordinates() {
    assertDoesNotThrow(() -> new Coordinates(-23.56391, -46.65239));
  }

  @ParameterizedTest
  @ValueSource(doubles = {0d, -100d, 100d})
  void shouldThrowExceptionWhenLatitudeIsInvalid(Double latitude) {
    assertThrows(ValidatorException.class, () -> new Coordinates(latitude, -46.65239));
  }

  @ParameterizedTest
  @ValueSource(doubles = {0d, -100d, 100d})
  void shouldThrowExceptionWhenLongitudeIsInvalid(Double longitude) {
    assertThrows(ValidatorException.class, () -> new Coordinates(-23.56391, longitude));
  }

}