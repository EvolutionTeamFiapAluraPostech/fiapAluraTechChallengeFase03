package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;

public interface CreateRestaurantBookingUseCase {

  Booking execute(String restaurantId, Booking booking);

}
