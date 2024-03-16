package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.presentation.dto.BookingFilter;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "RestaurantsBookingApi", description = "API de cadastro de reservas de restaurantes")
public interface RestaurantsBookingApi {

  @Operation(summary = "Reservas de restaurantes",
      description = "Endpoint para reservar mesa em restaurante em um determinado horário. O Necessário ID do restaurante, usuário solicitante, data e hora da reserva.",
      tags = "RestaurantsBookingApi")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = BookingOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação do ID do restaurante, ID do usuário solicitante, data e hora da reserva.",
          content = {@Content(schema = @Schema(hidden = true))})})
  BookingOutputDto postRestaurantBooking(
      @Parameter(description = "UUID do restaurante válido") String restaurantId,
      BookingInputDto bookingInputDto);

  @Operation(summary = "Lista de reservas de um restaurante",
      description = "Endpoint para recuperar uma lista de reservas de um restaurante selecionado",
      tags = {"RestaurantsBookingApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = BookingOutputDto.class))})})
  List<BookingOutputDto> getAllBookingByRestaurantIdAndStateAndBookingDateBetween(
      @Parameter(description = "UUID do restaurante válido") String restaurantId,
      @Parameter(description = "status da reserva e período da reserva") BookingFilter filter);

}
