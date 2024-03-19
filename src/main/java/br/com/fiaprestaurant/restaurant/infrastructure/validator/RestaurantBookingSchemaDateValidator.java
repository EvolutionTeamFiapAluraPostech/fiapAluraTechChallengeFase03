package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_DATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_DATE_MUST_BE_GREATER_THAN_ACTUAL_DATE;

import br.com.fiaprestaurant.restaurant.application.validator.RestaurantBookingDateValidator;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class RestaurantBookingSchemaDateValidator implements RestaurantBookingDateValidator {

  @Override
  public void validate(LocalDateTime bookingDate) {
    if (LocalDateTime.now().isAfter(bookingDate)) {
      var actualDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      var bookingDateMessage = bookingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DATE_FIELD,
              RESTAURANT_BOOKING_DATE_MUST_BE_GREATER_THAN_ACTUAL_DATE.formatted(actualDate,
                  bookingDateMessage)));
    }
  }
}
