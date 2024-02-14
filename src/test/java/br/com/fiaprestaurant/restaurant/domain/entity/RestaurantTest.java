package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantSchema;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class RestaurantTest {

  @Test
  void shouldCreateNewRestaurant() {
    var restaurantSchema = createRestaurantSchema();
    assertDoesNotThrow(restaurantSchema::createRestaurantFromRestaurantSchema);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "12"})
  void shouldThrowExceptionWhenRestaurantNameIsInvalid(String restaurantName) {
    var restaurantSchema = createRestaurantSchema();
    restaurantSchema.setName(restaurantName);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }

  @Test
  void shouldThrowExceptionWhenRestaurantNameLengthIsInvalid() {
    var restaurantSchema = createRestaurantSchema();
    var restaurantName = String.join("", Collections.nCopies(101, "a"));
    restaurantSchema.setName(restaurantName);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }
}