package br.com.fiaprestaurant.restaurant.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record BookingFilter(
    @Schema(example = "OPEN/CLOSED", description = "Status da reserva do restaurante.")
    String bookingState,
    @Schema(example = "2024-03-26 19:00:00", description = "Data e hora inicial da reserva.")
    LocalDateTime startBookingDate,
    @Schema(example = "2024-03-26 23:59:59", description = "Data e hora final da reserva.")
    LocalDateTime endBookingDate) {

}
