package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_DATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_STATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_ID_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_BOOKING_STATE;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_END_BOOKING_DATE;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_START_BOOKING_DATE;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_THE_RESTAURANT_ID;

import br.com.fiaprestaurant.restaurant.application.validator.GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

@Component
public class GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator implements
    GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator {

  @Override
  public void validate(String restaurantId, String state, LocalDateTime startBookingDate,
      LocalDateTime endBookingDate) {
    if (!StringUtils.hasText(restaurantId)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_ID_FIELD,
              ENTER_THE_RESTAURANT_ID));
    }
    if (!StringUtils.hasText(state)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_STATE_FIELD,
              ENTER_RESTAURANT_BOOKING_STATE));
    }
    if (startBookingDate == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DATE_FIELD,
              ENTER_RESTAURANT_START_BOOKING_DATE));
    }
    if (endBookingDate == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DATE_FIELD,
              ENTER_RESTAURANT_END_BOOKING_DATE));
    }
  }
}
