package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantBookingInputDto;
import java.util.UUID;

public final class RestaurantBookingTestData {


  private RestaurantBookingTestData() {
  }

  public static RestaurantBookingInputDto createRestaurantBookingInputDto(String restaurantId,
      String userId, String bookingDate) {
    return new RestaurantBookingInputDto(restaurantId, userId, bookingDate);
  }

}
