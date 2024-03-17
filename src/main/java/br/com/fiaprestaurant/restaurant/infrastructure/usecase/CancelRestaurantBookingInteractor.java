package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.mailer.RestaurantBookingMailer;
import br.com.fiaprestaurant.restaurant.application.usecase.CancelRestaurantBookingUseCase;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelRestaurantBookingInteractor implements CancelRestaurantBookingUseCase {

  private final BookingGateway bookingGateway;
  private final UuidValidator uuidValidator;
  private final RestaurantGateway restaurantGateway;
  private final UserGateway userGateway;
  private final RestaurantBookingMailer restaurantBookingMailer;

  public CancelRestaurantBookingInteractor(BookingGateway bookingGateway,
      UuidValidator uuidValidator, RestaurantGateway restaurantGateway, UserGateway userGateway,
      @Qualifier(value = "cancelRestaurantBookingMailer") RestaurantBookingMailer restaurantBookingMailer) {
    this.bookingGateway = bookingGateway;
    this.uuidValidator = uuidValidator;
    this.restaurantGateway = restaurantGateway;
    this.userGateway = userGateway;
    this.restaurantBookingMailer = restaurantBookingMailer;
  }

  @Transactional
  @Override
  public void execute(String restaurantId, String bookingId, String currentUsername) {
    uuidValidator.validate(restaurantId);
    uuidValidator.validate(bookingId);
    var restaurant = restaurantGateway.findByIdRequired(UUID.fromString(restaurantId));
    var booking = bookingGateway.findByIdRequired(UUID.fromString(bookingId));
    var user = userGateway.findByEmailRequired(currentUsername);
    bookingGateway.cancel(booking);
    restaurantBookingMailer.createAndSendEmail(booking, restaurant, user);
  }
}
