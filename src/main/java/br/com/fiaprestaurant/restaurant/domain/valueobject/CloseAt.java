package br.com.fiaprestaurant.restaurant.domain.valueobject;

import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields;
import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record CloseAt(String closeAtValue) {

  public CloseAt {
    validateCloseAtIsNullOrEmpty(closeAtValue);
    validateCloseAtIsAValidNumber(closeAtValue);
  }

  private void validateCloseAtIsAValidNumber(String closeAt) {
    if (closeAt.trim().length() != 5) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
    if (!closeAt.trim().contains(":")) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
    try {
      var hour = closeAt.split(":")[0];
      Integer.parseInt(hour);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
    try {
      var minute = closeAt.split(":")[1];
      Integer.parseInt(minute);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
  }


  private void validateCloseAtIsNullOrEmpty(String closeAt) {
    if (closeAt == null || closeAt.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD, RestaurantMessages.ENTER_CLOSING_TIME));
    }
  }
}
