package br.com.fiaprestaurant.restaurant.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {

  private UUID id;
  private UUID restaurantId;
  private UUID userId;
  private String description;
  private LocalDateTime bookingDate;
  private String bookingState;

  public Booking(UUID id, UUID restaurantId, UUID userId, String description, LocalDateTime bookingDate,
      String bookingState) {
    this(restaurantId, userId, description, bookingDate);
    this.id = id;
    this.bookingState = bookingState;
  }

  public Booking(UUID restaurantId, UUID userId, String description, LocalDateTime bookingDate) {
    this.restaurantId = restaurantId;
    this.userId = userId;
    this.description = description;
    this.bookingDate = bookingDate;
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
