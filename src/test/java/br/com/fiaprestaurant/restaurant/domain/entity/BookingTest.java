package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BookingTest {

  @Test
  void shouldCreateNewBooking() {
    var bookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(DEFAULT_RESTAURANT_ID_STRING,
        DEFAULT_USER_UUID_FROM_STRING, RESTAURANT_BOOKING_DESCRIPTION, bookingDate.toString());
    assertThat(booking).isNotNull();
    assertThat(booking.getId()).isNotNull();
    assertThat(booking.getRestaurantId()).isNotNull().isEqualTo(DEFAULT_RESTAURANT_ID);
    assertThat(booking.getUserId()).isNotNull().isEqualTo(DEFAULT_USER_UUID);
    assertThat(booking.getBookingDate()).isNotNull().isEqualTo(bookingDate);
    assertThat(booking.getBookingState()).isNotBlank()
        .isEqualTo(BookingState.RESERVED.name());
    assertThat(booking.getDescription()).isNotBlank().isEqualTo(RESTAURANT_BOOKING_DESCRIPTION);
  }

  @Test
  void shouldThrowExceptionWhenBookingDateIsNull() {
    assertThatThrownBy(
        () -> createRestaurantBooking(DEFAULT_RESTAURANT_ID_STRING, DEFAULT_USER_UUID_FROM_STRING,
            RESTAURANT_BOOKING_DESCRIPTION, null))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowExceptionWhenBookingDateIsInvalid() {
    var bookingDate = LocalDateTime.now().minusMinutes(1).toString();
    assertThatThrownBy(
        () -> createRestaurantBooking(DEFAULT_RESTAURANT_ID_STRING, DEFAULT_USER_UUID_FROM_STRING,
            RESTAURANT_BOOKING_DESCRIPTION, bookingDate))
        .isInstanceOf(ValidatorException.class);
  }

}