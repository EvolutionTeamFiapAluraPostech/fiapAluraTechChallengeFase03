package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_TYPE_OF_CUISINE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class TypeOfCuisineTest {

  @Test
  void shouldCreateNewTypeOfCuisine() {
    assertDoesNotThrow(() -> new TypeOfCuisine(DEFAULT_RESTAURANT_TYPE_OF_CUISINE));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "12"})
  void shouldThrowExceptionWhenTypeOfCuisineIsInvalid(String typeOfCuisineDescription) {
    assertThrows(ValidatorException.class, () -> new TypeOfCuisine(typeOfCuisineDescription));
  }

  @Test
  void shouldThrowExceptionWhenTypeOfCuisineLengthIsInvalid() {
    var typeOfCuisineDescription = String.join("", Collections.nCopies(51, "a"));
    assertThrows(ValidatorException.class, () -> new TypeOfCuisine(typeOfCuisineDescription));
  }

}