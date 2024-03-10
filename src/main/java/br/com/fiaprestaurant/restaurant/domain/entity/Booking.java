package br.com.fiaprestaurant.restaurant.domain.entity;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_DATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_BOOKING_DATE;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_DATE_MUST_BE_GREATER_THAN_ACTUAL_DATE;

import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingStateEnum;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.validation.FieldError;

public class Booking {

  private UUID id;
  private final UUID restaurantId;
  private final UUID userId;
  private final String description;
  private final LocalDateTime bookingDate;
  private BookingState bookingState;

  public Booking(UUID id, UUID restaurantId, UUID userId, String description,
      LocalDateTime bookingDate, String bookingState) {
    this(restaurantId, userId, description, bookingDate);
    this.id = id;
    this.bookingState = new BookingState(bookingState);
  }

  public Booking(UUID restaurantId, UUID userId, String description, LocalDateTime bookingDate) {
    validateBookingDateIsNull(bookingDate);
    validateBookingDate(bookingDate);
    this.restaurantId = restaurantId;
    this.userId = userId;
    this.description = description;
    this.bookingDate = bookingDate;
    this.bookingState = new BookingState(BookingStateEnum.RESERVED.getLabel());
  }

  private void validateBookingDateIsNull(LocalDateTime bookingDate) {
    if (bookingDate == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DATE_FIELD,
              ENTER_RESTAURANT_BOOKING_DATE));
    }
  }

  private void validateBookingDate(LocalDateTime bookingDate) {
    if (LocalDateTime.now().isAfter(bookingDate)) {
      var actualDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      var bookingDateMessage = bookingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_DATE_FIELD,
              RESTAURANT_BOOKING_DATE_MUST_BE_GREATER_THAN_ACTUAL_DATE.formatted(actualDate,
                  bookingDateMessage)));
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
    return bookingState.getValue();
  }
}
