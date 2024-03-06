package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantBookingInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantBookingOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "RestaurantsBookingApi", description = "API de cadastro de reservas de restaurantes")
public interface RestaurantsBookingApi {

  @Operation(summary = "Reservas de restaurantes",
      description = "Endpoint para reservar mesa em restaurante em um determinado horário. O Necessário ID do restaurante, usuário solicitante, data e hora da reserva.",
      tags = "RestaurantsBookingApi")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantBookingOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação do ID do restaurante, ID do usuário solicitante, data e hora da reserva.",
          content = {@Content(schema = @Schema(hidden = true))})})
  RestaurantBookingOutputDto postRestaurantBooking(
      @Parameter(description = "UUID do restaurante válido") String restaurantId,
      RestaurantBookingInputDto restaurantBookingInputDto);

}
