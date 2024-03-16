package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID_STRING;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidatorTest {

  @Spy
  private GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator;

  @Test
  void shouldValidateGetAllBookingParameters() {
    var state = BookingState.RESERVED.name();
    var startBookingDate = LocalDateTime.now();
    var endBookingDate = LocalDateTime.now();
    assertThatCode(
        () -> getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator.validate(
            DEFAULT_RESTAURANT_ID_STRING, state, startBookingDate,
            endBookingDate)).doesNotThrowAnyException();
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantIdWasNotFilled(String restaurantId) {
    var state = BookingState.RESERVED.name();
    var startBookingDate = LocalDateTime.now();
    var endBookingDate = LocalDateTime.now();
    assertThatCode(
        () -> getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator.validate(
            restaurantId, state, startBookingDate, endBookingDate)).doesNotThrowAnyException();
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenBookingStateWasNotFilled(String state) {
    var startBookingDate = LocalDateTime.now();
    var endBookingDate = LocalDateTime.now();
    assertThatCode(
        () -> getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator.validate(
            DEFAULT_RESTAURANT_ID_STRING, state, startBookingDate,
            endBookingDate)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenStartBookingDateWasNotFilled() {
    var state = BookingState.RESERVED.name();
    var endBookingDate = LocalDateTime.now();
    assertThatThrownBy(
        () -> getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator.validate(
            DEFAULT_RESTAURANT_ID_STRING, state, null, endBookingDate))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowExceptionWhenEndBookingDateWasNotFilled() {
    var state = BookingState.RESERVED.name();
    var startBookingDate = LocalDateTime.now();
    assertThatThrownBy(
        () -> getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator.validate(
            DEFAULT_RESTAURANT_ID_STRING, state, startBookingDate,
            null)).isInstanceOf(ValidatorException.class);
  }
}
