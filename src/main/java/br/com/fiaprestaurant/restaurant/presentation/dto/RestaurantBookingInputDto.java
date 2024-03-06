package br.com.fiaprestaurant.restaurant.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

@Tag(name = "RestaurantBookingInputDto", description = "DTO de entrada de dados da reserva de restaurante.")
public record RestaurantBookingInputDto(
    @NotBlank
    @UUID
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do restaurante.")
    String restaurantId,
    @NotBlank
    @UUID
    @Schema(example = "bae0fc3d-be9d-472a-bf03-7a7ee2411ce1", description = "Identificador único do usuário.")
    String userId,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    String bookingDate) {

}
