package br.com.fiaprestaurant.restaurant.application.validator;

import java.time.LocalDateTime;

public interface GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator {

  void validate(String restaurantId, String state, LocalDateTime startBookingDate,
      LocalDateTime endBookingDate);

}
