package br.com.fiaprestaurant.restaurant.domain.valueobject;

import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields;
import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record OpenAt(String openAtValue) {

  public OpenAt {
    validateOpenAtIsNullOrEmpty(openAtValue);
    validateOpenAtIsAValidNumber(openAtValue);
  }

  private void validateOpenAtIsAValidNumber(String openAt) {
    if (openAt.trim().length() != 5) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
    if (!openAt.trim().contains(":")) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
    try {
      var hour = openAt.split(":")[0];
      Integer.parseInt(hour);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
    try {
      var minute = openAt.split(":")[1];
      Integer.parseInt(minute);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
  }

  private void validateOpenAtIsNullOrEmpty(String openAt) {
    if (openAt == null || openAt.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_OPENING_HOURS));
    }
  }
}
