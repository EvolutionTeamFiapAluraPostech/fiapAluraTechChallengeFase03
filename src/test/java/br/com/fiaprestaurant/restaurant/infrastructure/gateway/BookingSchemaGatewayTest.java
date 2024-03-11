package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBookingSchema;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.BookingSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingSchemaGatewayTest {

  @Mock
  private BookingSchemaRepository bookingSchemaRepository;
  @InjectMocks
  private BookingSchemaGateway bookingSchemaGateway;

  private List<BookingSchema> createBookingSchemas(Restaurant restaurant,
      LocalDateTime startBookingDate) {
    List<BookingSchema> bookingSchemas = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      var booking = createRestaurantBooking(restaurant.getId().toString(),
          DEFAULT_USER_UUID_FROM_STRING, RESTAURANT_BOOKING_DESCRIPTION,
          startBookingDate.toString());
      var bookingSchema = createRestaurantBookingSchema(booking);
      bookingSchema.setId(booking.getId());
      bookingSchemas.add(bookingSchema);
    }
    return bookingSchemas;
  }

  @Test
  void shouldSaveBooking() {
    var booking = createRestaurantBooking(DEFAULT_RESTAURANT_ID_STRING,
        DEFAULT_USER_UUID_FROM_STRING, RESTAURANT_BOOKING_DESCRIPTION,
        LocalDateTime.now().plusDays(1).toString());
    var bookingSchema = createRestaurantBookingSchema(booking);
    var bookingSchemaSaved = createRestaurantBookingSchema(booking);
    bookingSchemaSaved.setId(booking.getId());
    when(bookingSchemaRepository.save(bookingSchema)).thenReturn(bookingSchemaSaved);

    var bookingSaved = bookingSchemaGateway.save(booking);

    assertThat(bookingSaved).isNotNull();
    assertThat(bookingSaved.getId()).isNotNull().isEqualTo(booking.getId());
  }

  @Test
  void shouldReturnFalseWhenRestaurantBookingIsNotOverLimit() {
    var restaurant = RestaurantTestData.createRestaurant();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var endBookingDate = startBookingDate.plusHours(1);
    var booking = createRestaurantBooking(restaurant.getId().toString(),
        DEFAULT_USER_UUID_FROM_STRING, RESTAURANT_BOOKING_DESCRIPTION,
        startBookingDate.toString());
    var bookingSchema = createRestaurantBookingSchema(booking);
    bookingSchema.setId(booking.getId());
    var bookingSchemas = List.of(bookingSchema);
    when(
        bookingSchemaRepository.findBookingSchemaByRestaurantSchemaIdAndBookingStateAndBookingDateBetween(
            booking.getRestaurantId(), BookingState.RESERVED.name(), startBookingDate,
            endBookingDate)).thenReturn(
        bookingSchemas);

    var isOverBooking = bookingSchemaGateway.restaurantBookingIsOverLimit(restaurant,
        startBookingDate, endBookingDate);

    assertThat(isOverBooking).isFalse();
  }

  @Test
  void shouldReturnTrueWhenRestaurantBookingIsOverLimit() {
    var restaurant = RestaurantTestData.createRestaurant();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var endBookingDate = startBookingDate.plusHours(1);
    var bookingSchemas = createBookingSchemas(restaurant, startBookingDate);
    when(
        bookingSchemaRepository.findBookingSchemaByRestaurantSchemaIdAndBookingStateAndBookingDateBetween(
            restaurant.getId(), BookingState.RESERVED.name(), startBookingDate,
            endBookingDate)).thenReturn(
        bookingSchemas);

    var isOverBooking = bookingSchemaGateway.restaurantBookingIsOverLimit(restaurant,
        startBookingDate, endBookingDate);

    assertThat(isOverBooking).isTrue();
  }

}