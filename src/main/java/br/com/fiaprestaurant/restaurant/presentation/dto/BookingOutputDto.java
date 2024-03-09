package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import io.swagger.v3.oas.annotations.media.Schema;

public record BookingOutputDto(
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único da reserva do restaurante.")
    String id,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do restaurante.")
    String restaurantId,
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do usuário.")
    String userId,
    @Schema(example = "2024-01-3-26 20:00:00", description = "Data e hora da reserva.")
    String bookingDate,
    @Schema(example = "OPEN/CLOSED", description = "Status da reserva do restaurante.")
    String bookingState) {

  public static BookingOutputDto from(Booking bookingSaved) {
    return new BookingOutputDto(bookingSaved.getId().toString(),
        bookingSaved.getRestaurantId().toString(),
        bookingSaved.getUserId().toString(),
        bookingSaved.getBookingDate().toString(),
        bookingSaved.getBookingState());
  }
}
