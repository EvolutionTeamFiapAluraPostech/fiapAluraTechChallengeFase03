package br.com.fiaprestaurant.restaurant.presentation.dto;

import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Tag(name = "RestaurantBookingInputDto", description = "DTO de entrada de dados da reserva de restaurante.")
public record BookingInputDto(
    @NotBlank
    @org.hibernate.validator.constraints.UUID
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do restaurante.")
    String restaurantId,
    @NotBlank
    @org.hibernate.validator.constraints.UUID
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do usuário.")
    String userId,
    @Schema(example = "Mesa próxima a uma janela", description = "Uma observação referente a reserva.")
    String description,
    @Schema(example = "2024-03-26 20:00:00", description = "Data e hora da reserva.")
    LocalDateTime bookingDate) {

  public Booking toBookingfrom() {
    return new Booking(UUID.fromString(restaurantId), UUID.fromString(userId), description,
        bookingDate);
  }

}
