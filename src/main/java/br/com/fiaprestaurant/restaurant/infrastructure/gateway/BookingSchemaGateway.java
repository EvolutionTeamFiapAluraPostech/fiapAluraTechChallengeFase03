package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.BookingSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

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
    var bookingSchemas = bookingSchemaRepository.findBookingSchemaByRestaurantSchemaIdAndBookingDateBetween(
        restaurant.getId(), startBookingDate, endBookingDate);
    var restaurantBookingPeopleCount = getRestaurantBookingPeopleCount(bookingSchemas);
    return restaurantBookingPeopleCount > restaurant.getPeopleCapacity().peopleCapacityValue();
  }

  private long getRestaurantBookingPeopleCount(List<BookingSchema> bookingSchemas) {
    return (long) bookingSchemas.size() * NUMBER_OF_PEOPLE_PER_BOOKING;
  }

  private BookingSchema convertToBookingSchemaFrom(Booking booking) {
    return BookingSchema.builder()
        .restaurantSchema(RestaurantSchema.builder().id(booking.getRestaurantId()).build())
        .userSchema(UserSchema.builder().id(booking.getUserId()).build())
        .bookingDate(booking.getBookingDate())
        .bookingState(booking.getBookingState())
        .build();
  }

}
