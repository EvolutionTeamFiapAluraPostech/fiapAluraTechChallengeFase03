package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "RestaurantsApi", description = "API de manutenção de restaurantes")
public interface RestaurantsApi {

  @Operation(summary = "Cadastro de restaurantes",
      description = "Endpoint para cadastrar novos restaurantes. O nome, CNPJ, tipo de cozinha, endereço, capacidade, horário de abertura e fechamento são obrigatórios. O CNPJ será único na base de dados.",
      tags = "RestaurantApi")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação de nome, CNPJ, tipo de cozinha, endereço, capacidade, horário de abertura e fechamento.",
          content = {@Content(schema = @Schema(hidden = true))})})
  RestaurantOutputDto postRestaurant(RestaurantInputDto restaurantInputDto);

}
