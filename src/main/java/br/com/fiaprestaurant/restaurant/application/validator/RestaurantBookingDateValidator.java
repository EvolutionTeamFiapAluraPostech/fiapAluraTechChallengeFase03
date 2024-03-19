package br.com.fiaprestaurant.restaurant.application.validator;

import java.time.LocalDateTime;

public interface RestaurantBookingDateValidator {

  void validate(LocalDateTime bookingDate);

}
