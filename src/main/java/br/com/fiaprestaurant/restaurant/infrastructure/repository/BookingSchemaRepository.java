package br.com.fiaprestaurant.restaurant.infrastructure.repository;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSchemaRepository extends JpaRepository<BookingSchema, UUID> {

  List<BookingSchema> findBookingSchemaByRestaurantSchemaIdAndBookingStateAndBookingDateBetween(
      UUID restaurantId, String bookingState, LocalDateTime startBookingDate,
      LocalDateTime endBookingDate);

}
