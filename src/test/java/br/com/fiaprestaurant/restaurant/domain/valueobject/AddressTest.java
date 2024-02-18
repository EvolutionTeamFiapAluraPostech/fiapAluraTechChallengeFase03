package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_CITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NEIGHBORHOOD;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NUMBER;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_POSTAL_CODE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_STATE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_STREET;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import br.com.fiaprestaurant.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class AddressTest {

  @Test
  void shouldCreateAddress() {
    assertDoesNotThrow(
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
            DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenStreetIsNullOrEmpty(String street) {
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, street,
            DEFAULT_RESTAURANT_NUMBER,
            DEFAULT_RESTAURANT_NEIGHBORHOOD, DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @Test
  void shouldThrowExceptionWhenStreetLengthIsBetween3And255Characters() {
    var street = StringUtil.generateStringLength(256);
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, street,
            DEFAULT_RESTAURANT_NUMBER,
            DEFAULT_RESTAURANT_NEIGHBORHOOD, DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenNeighborhoodIsNullOrEmpty(String neighborhood) {
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER,
            neighborhood, DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @Test
  void shouldThrowExceptionWhenNeighborhoodLengthIsBetween3And100Characters() {
    var neighborhood = StringUtil.generateStringLength(101);
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER,
            neighborhood, DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCityIsNullOrEmpty(String city) {
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER,
            DEFAULT_RESTAURANT_NEIGHBORHOOD, city, DEFAULT_RESTAURANT_STATE,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @Test
  void shouldThrowExceptionWhenCityLengthIsBetween3And100Characters() {
    var city = StringUtil.generateStringLength(101);
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER,
            DEFAULT_RESTAURANT_NEIGHBORHOOD, city, DEFAULT_RESTAURANT_STATE,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenStateIsNullOrEmpty(String state) {
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER,
            DEFAULT_RESTAURANT_NEIGHBORHOOD, DEFAULT_RESTAURANT_CITY, state,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

  @Test
  void shouldThrowExceptionWhenStateLengthIsDifferentTo2Characters() {
    var state = StringUtil.generateStringLength(3);
    assertThrows(ValidatorException.class,
        () -> new Address(DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE,
            DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER,
            DEFAULT_RESTAURANT_NEIGHBORHOOD, DEFAULT_RESTAURANT_CITY, state,
            DEFAULT_RESTAURANT_POSTAL_CODE));
  }

}