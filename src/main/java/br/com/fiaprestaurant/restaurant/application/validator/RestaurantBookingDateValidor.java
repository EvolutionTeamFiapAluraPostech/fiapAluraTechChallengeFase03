package br.com.fiaprestaurant.restaurant.application.validator;

import java.time.LocalDateTime;

public interface RestaurantBookingDateValidor {

  void validate(LocalDateTime bookingDate);

}
