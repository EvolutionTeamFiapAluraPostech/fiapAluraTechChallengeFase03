package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.presentation.dto.BookingInputDto;

public final class RestaurantBookingTestData {

  public static final String RESTAURANT_BOOKING_DESCRIPTION = "Mesa longe da porta da cozinha";

  private RestaurantBookingTestData() {
  }

  public static BookingInputDto createRestaurantBookingInputDto(String restaurantId,
      String userId, String description, String bookingDate) {
    return new BookingInputDto(restaurantId, userId, description, bookingDate);
  }

}
