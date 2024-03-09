package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_PEOPLE_CAPACITY_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_PEOPLE_CAPACITY_MUST_BE_LOWER_THAN_MAX_RESTAURANT_PEOPLE_CAPACITY;

import br.com.fiaprestaurant.restaurant.application.validator.RestaurantBookingCapacityOfPeopleValidator;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.infrastructure.gateway.BookingSchemaGateway;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class RestaurantBookingSchemaCapacityOfPeopleValidator implements
    RestaurantBookingCapacityOfPeopleValidator {

  private final BookingSchemaGateway bookingSchemaGateway;

  public RestaurantBookingSchemaCapacityOfPeopleValidator(
      BookingSchemaGateway bookingSchemaGateway) {
    this.bookingSchemaGateway = bookingSchemaGateway;
  }

  @Override
  public void validate(Restaurant restaurant, Booking booking) {
    var startBookingDate = booking.getBookingDate();
    var endBookingDate = booking.getBookingDate().plusHours(1);
    var restaurantBookingIsOverLimit = bookingSchemaGateway.restaurantBookingIsOverLimit(restaurant,
        startBookingDate, endBookingDate);
    if (restaurantBookingIsOverLimit) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_PEOPLE_CAPACITY_FIELD,
              RESTAURANT_BOOKING_PEOPLE_CAPACITY_MUST_BE_LOWER_THAN_MAX_RESTAURANT_PEOPLE_CAPACITY));
    }
  }
}
