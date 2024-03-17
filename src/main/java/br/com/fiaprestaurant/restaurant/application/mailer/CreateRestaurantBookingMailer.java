package br.com.fiaprestaurant.restaurant.application.mailer;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.user.domain.entity.User;

public interface CreateRestaurantBookingMailer {

  void createAndSendEmail(Booking booking, Restaurant restaurant, User user);

}
