package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantSchema;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
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

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantOpenAtIsNullOrEmpty(String openAt) {
    var restaurantSchema = createRestaurantSchema();
    restaurantSchema.setOpenAt(openAt);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1000", "10000", "1A:00", "10:A0"})
  void shouldThrowExceptionWhenRestaurantOpenAtIsInvalid(String openAt) {
    var restaurantSchema = createRestaurantSchema();
    restaurantSchema.setOpenAt(openAt);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantCloseAtIsNullOrEmpty(String closeAt) {
    var restaurantSchema = createRestaurantSchema();
    restaurantSchema.setCloseAt(closeAt);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1000", "10000", "1A:00", "10:A0"})
  void shouldThrowExceptionWhenRestaurantCloseAtIsInvalid(String openAt) {
    var restaurantSchema = createRestaurantSchema();
    restaurantSchema.setCloseAt(openAt);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void shouldThrowExceptionWhenRestaurantPeopleCapacityIsInvalid(Integer peopleCapacity) {
    var restaurantSchema = createRestaurantSchema();
    restaurantSchema.setPeopleCapacity(peopleCapacity);
    assertThrows(ValidatorException.class, restaurantSchema::createRestaurantFromRestaurantSchema);
  }

}