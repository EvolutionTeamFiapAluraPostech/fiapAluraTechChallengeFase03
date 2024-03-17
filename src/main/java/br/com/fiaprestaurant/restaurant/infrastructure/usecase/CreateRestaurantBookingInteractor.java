package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.mailer.CreateRestaurantBookingMailer;
import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantBookingUseCase;
import br.com.fiaprestaurant.restaurant.application.validator.RestaurantBookingCapacityOfPeopleValidator;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRestaurantBookingInteractor implements CreateRestaurantBookingUseCase {

  private final BookingGateway bookingGateway;
  private final RestaurantGateway restaurantGateway;
  private final UserGateway userGateway;
  private final UuidValidator uuidValidator;
  private final RestaurantBookingCapacityOfPeopleValidator restaurantBookingCapacityOfPeopleValidator;
  private final CreateRestaurantBookingMailer createRestaurantBookingMailer;

  public CreateRestaurantBookingInteractor(BookingGateway bookingGateway,
      RestaurantGateway restaurantGateway, UserGateway userGateway, UuidValidator uuidValidator,
      RestaurantBookingCapacityOfPeopleValidator restaurantBookingCapacityOfPeopleValidator,
      CreateRestaurantBookingMailer createRestaurantBookingMailer) {
    this.bookingGateway = bookingGateway;
    this.restaurantGateway = restaurantGateway;
    this.userGateway = userGateway;
    this.uuidValidator = uuidValidator;
    this.restaurantBookingCapacityOfPeopleValidator = restaurantBookingCapacityOfPeopleValidator;
    this.createRestaurantBookingMailer = createRestaurantBookingMailer;
  }

  @Transactional
  @Override
  public Booking execute(String restaurantId, Booking booking) {
    uuidValidator.validate(restaurantId);
    var user = userGateway.findUserByIdRequired(booking.getUserId());
    var restaurant = restaurantGateway.findByIdRequired(UUID.fromString(restaurantId));
    restaurantBookingCapacityOfPeopleValidator.validate(restaurant, booking);
    var bookingSaved = bookingGateway.save(booking);
    createRestaurantBookingMailer.createAndSendEmail(booking, restaurant, user);
    return bookingSaved;
  }
}
