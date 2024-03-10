package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_STATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_BOOKING_STATE;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

public class BookingState {

  private final String value;

  public BookingState(String value) {
    validateBookingState(value);
    this.value = value;
  }

  private void validateBookingState(String value) {
    if (!StringUtils.hasText(value)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_STATE_FIELD,
              ENTER_RESTAURANT_BOOKING_STATE));
    }
  }

  public String getValue() {
    return value;
  }
}
