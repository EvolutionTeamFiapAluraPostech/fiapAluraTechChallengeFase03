package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import java.time.LocalDateTime;
import java.util.List;

public interface GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase {

  List<Booking> execute(String restaurantId, String bookingState, LocalDateTime startBooking,
      LocalDateTime endBooking);

}
