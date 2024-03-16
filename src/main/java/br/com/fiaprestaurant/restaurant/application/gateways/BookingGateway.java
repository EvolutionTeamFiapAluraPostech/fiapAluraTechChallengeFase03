package br.com.fiaprestaurant.restaurant.application.gateways;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingGateway {

  Booking save(Booking booking);

  boolean restaurantBookingIsOverLimit(Restaurant restaurant, LocalDateTime startBookingDate,
      LocalDateTime endBookingDate);

  List<Booking> findBookingByRestaurantIdAndBookingStateAndBookingDateBetween(UUID restaurantId,
      String bookingState, LocalDateTime startBookingDate, LocalDateTime endBookingDate);
}
