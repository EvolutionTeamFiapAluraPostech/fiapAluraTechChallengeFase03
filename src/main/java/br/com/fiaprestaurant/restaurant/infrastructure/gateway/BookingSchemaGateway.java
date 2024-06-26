package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_ID_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_BOOKING_STATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_ALREADY_CANCELED;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_ALREADY_CLOSED;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_BOOKING_NOT_FOUND_WITH_ID;
import static br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState.CANCELED;
import static br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState.CLOSED;
import static br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState.RESERVED;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.BookingSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class BookingSchemaGateway implements BookingGateway {

  public static final int NUMBER_OF_PEOPLE_PER_BOOKING = 4;
  private final BookingSchemaRepository bookingSchemaRepository;

  public BookingSchemaGateway(BookingSchemaRepository bookingSchemaRepository) {
    this.bookingSchemaRepository = bookingSchemaRepository;
  }

  @Override
  public Booking save(Booking booking) {
    var bookingSchema = convertToBookingSchemaFrom(booking);
    var bookingSchemaSaved = bookingSchemaRepository.save(bookingSchema);
    return bookingSchemaSaved.toBooking();
  }

  @Override
  public boolean restaurantBookingIsOverLimit(Restaurant restaurant, LocalDateTime startBookingDate,
      LocalDateTime endBookingDate) {
    var bookingSchemas = bookingSchemaRepository.findBookingSchemaByRestaurantSchemaIdAndBookingStateAndBookingDateBetween(
        restaurant.getId(), RESERVED.name(), startBookingDate, endBookingDate);
    var restaurantBookingPeopleCount = getRestaurantBookingPeopleCount(bookingSchemas);
    return restaurantBookingPeopleCount >= restaurant.getPeopleCapacity().peopleCapacityValue();
  }

  @Override
  public List<Booking> findBookingByRestaurantIdAndBookingStateAndBookingDateBetween(
      UUID restaurantId, String bookingState, LocalDateTime startBookingDate,
      LocalDateTime endBookingDate) {
    var bookingSchemas = bookingSchemaRepository.findBookingSchemaByRestaurantSchemaIdAndBookingStateAndBookingDateBetween(
        restaurantId, bookingState, startBookingDate, endBookingDate);
    return bookingSchemas.stream().map(BookingSchema::toBooking).toList();
  }

  @Override
  public Booking findByIdRequired(UUID bookingId) {
    var bookingSchema = bookingSchemaRepository.findById(bookingId).orElseThrow(
        () -> new NoResultException(
            new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_ID_FIELD,
                RESTAURANT_BOOKING_NOT_FOUND_WITH_ID.formatted(bookingId))));
    return bookingSchema.toBooking();
  }

  @Override
  public void cancel(Booking booking) {
    var bookingFound = isBookingAlreadyClosedOrCanceled(booking);
    var bookingSchema = convertToBookingSchemaFrom(bookingFound);
    bookingSchema.setId(booking.getId());
    bookingSchema.setBookingState(CANCELED.name());
    bookingSchemaRepository.save(bookingSchema);
  }

  @Override
  public void close(Booking booking) {
    var bookingFound = isBookingAlreadyClosedOrCanceled(booking);
    var bookingSchema = convertToBookingSchemaFrom(bookingFound);
    bookingSchema.setId(booking.getId());
    bookingSchema.setBookingState(CLOSED.name());
    bookingSchemaRepository.save(bookingSchema);
  }

  private Booking isBookingAlreadyClosedOrCanceled(Booking booking) {
    var bookingFound = findByIdRequired(booking.getId());
    if (CLOSED.name().equals(bookingFound.getBookingState())) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_STATE_FIELD,
              RESTAURANT_BOOKING_ALREADY_CLOSED));
    }
    if (CANCELED.name().equals(bookingFound.getBookingState())) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), RESTAURANT_BOOKING_STATE_FIELD,
              RESTAURANT_BOOKING_ALREADY_CANCELED));
    }
    return bookingFound;
  }

  private long getRestaurantBookingPeopleCount(List<BookingSchema> bookingSchemas) {
    return (long) bookingSchemas.size() * NUMBER_OF_PEOPLE_PER_BOOKING;
  }

  private BookingSchema convertToBookingSchemaFrom(Booking booking) {
    return BookingSchema.builder()
        .restaurantSchema(RestaurantSchema.builder().id(booking.getRestaurantId()).build())
        .userSchema(UserSchema.builder().id(booking.getUserId()).build())
        .description(booking.getDescription())
        .bookingDate(booking.getBookingDate())
        .bookingState(booking.getBookingState())
        .build();
  }

}
