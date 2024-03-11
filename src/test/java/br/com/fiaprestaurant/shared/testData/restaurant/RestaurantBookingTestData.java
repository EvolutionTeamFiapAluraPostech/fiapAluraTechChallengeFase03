package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingInputDto;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.time.LocalDateTime;
import java.util.UUID;

public final class RestaurantBookingTestData {

  public static final String RESTAURANT_BOOKING_DESCRIPTION = "Mesa longe da porta da cozinha";

  private RestaurantBookingTestData() {
  }

  public static BookingInputDto createRestaurantBookingInputDto(String restaurantId,
      String userId, String description, LocalDateTime bookingDate) {
    return new BookingInputDto(restaurantId, userId, description, bookingDate);
  }

  public static Booking createRestaurantBooking(String restaurantId,
      String userId, String description, String bookingDate) {
    return new Booking(UUID.randomUUID(), UUID.fromString(restaurantId), UUID.fromString(userId),
        description, (bookingDate != null ? LocalDateTime.parse(bookingDate) : null),
        BookingState.RESERVED.name());
  }

  public static BookingSchema createRestaurantBookingSchema(Booking booking) {
    return BookingSchema.builder()
        .restaurantSchema(RestaurantSchema.builder().id(booking.getRestaurantId()).build())
        .userSchema(UserSchema.builder().id(booking.getUserId()).build())
        .bookingDate(booking.getBookingDate())
        .bookingState(booking.getBookingState())
        .description(booking.getDescription())
        .build();
  }

}
