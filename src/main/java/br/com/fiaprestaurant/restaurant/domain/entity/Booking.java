package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_DATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_DESCRIPTION_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_BOOKING_DATE;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_DESCRIPTION_LENGTH_MUST_HAVE_BETWEEN_3_AND_500_CHARACTERS;

import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;

public class Booking {

  private UUID id;
  private final UUID restaurantId;
  private final UUID userId;
  private final String description;
  private final LocalDateTime bookingDate;
  private String bookingState;

  public Booking(UUID id, UUID restaurantId, UUID userId, String description,
      LocalDateTime bookingDate, String bookingState) {
    this(restaurantId, userId, description, bookingDate);
    this.id = id;
    this.bookingState = bookingState;
  }

  public Booking(UUID restaurantId, UUID userId, String description, LocalDateTime bookingDate) {
    validateBookingDateIsNull(bookingDate);
    validateBookingDescription(description);
    this.restaurantId = restaurantId;
    this.userId = userId;
    this.description = description;
    this.bookingDate = bookingDate;
    this.bookingState = BookingState.RESERVED.name();
  }

  private void validateBookingDescription(String description) {
    if (StringUtils.hasText(description)) {
      var descriptionLength = description.trim().length();
      if (descriptionLength > 500) {
        throw new ValidatorException(
            new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DESCRIPTION_FIELD,
                RESTAURANT_BOOKING_DESCRIPTION_LENGTH_MUST_HAVE_BETWEEN_3_AND_500_CHARACTERS.formatted(
                    descriptionLength)));
      }
    }
  }

  private void validateBookingDateIsNull(LocalDateTime bookingDate) {
    if (bookingDate == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DATE_FIELD,
              ENTER_RESTAURANT_BOOKING_DATE));
    }
  }

  public UUID getId() {
    return id;
  }

  public UUID getRestaurantId() {
    return restaurantId;
  }

  public UUID getUserId() {
    return userId;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getBookingDate() {
    return bookingDate;
  }

  public String getBookingState() {
    return bookingState;
  }
}
