package br.com.fiaprestaurant.restaurant.application.validator;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;

public interface RestaurantBookingCapacityOfPeopleValidator {

  void validate(Restaurant restaurant, Booking booking);

}
