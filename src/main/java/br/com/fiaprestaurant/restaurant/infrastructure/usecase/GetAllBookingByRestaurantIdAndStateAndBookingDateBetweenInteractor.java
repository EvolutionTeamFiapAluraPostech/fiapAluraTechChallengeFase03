package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.infrastructure.validator.GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenInteractor implements
    GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase {

  private final BookingGateway bookingGateway;
  private final UuidValidator uuidValidator;
  private final GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator getAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenValidator;
  private final RestaurantGateway restaurantGateway;

  public GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenInteractor(
      BookingGateway bookingGateway,
      UuidValidator uuidValidator,
      GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator getAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenValidator,
      RestaurantGateway restaurantGateway) {
    this.bookingGateway = bookingGateway;
    this.uuidValidator = uuidValidator;
    this.getAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenValidator = getAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenValidator;
    this.restaurantGateway = restaurantGateway;
  }

  @Override
  public List<Booking> execute(String restaurantId, String bookingState,
      LocalDateTime startBookingDate, LocalDateTime endBookingDate) {
    uuidValidator.validate(restaurantId);
    getAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenValidator.validate(restaurantId,
        bookingState, startBookingDate, endBookingDate);
    restaurantGateway.findByIdRequired(UUID.fromString(restaurantId));
    return bookingGateway.findBookingByRestaurantIdAndBookingStateAndBookingDateBetween(
        UUID.fromString(restaurantId), bookingState, startBookingDate, endBookingDate);
  }
}
