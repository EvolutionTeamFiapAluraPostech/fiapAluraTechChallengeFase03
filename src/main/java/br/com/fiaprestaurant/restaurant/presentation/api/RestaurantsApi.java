package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantFilter;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

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

  @Operation(summary = "Lista de restaurantes",
      description = "Endpoint para recuperar uma lista de restaurantes, filtrada por nome ou coordenadas (latitude e longitude) ou tipo de cozinha, ordenada por nome",
      tags = {"RestaurantApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOutputDto.class))}),
      @ApiResponse(responseCode = "404", description = "not found para restaurante não encontrado",
          content = {@Content(schema = @Schema(hidden = true))})})
  List<RestaurantOutputDto> getRestaurantByNameOrCoordinatesOrTypeOfCuisine(
      RestaurantFilter restaurantFilter);

  @Operation(summary = "Atualiza restaurante",
      description = "Endpoint para atualizar dados do restaurante",
      tags = {"RestaurantApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para UUID inválido", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "404", description = "not found para restaurante não encontrado", content = {
          @Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "409", description = "conflic para CNPJ já cadastrado em outro restaurante", content = {
          @Content(schema = @Schema(hidden = true))})})
  RestaurantOutputDto putRestaurant(
      @Parameter(description = "UUID do restaurante válido") String id,
      @Parameter(description = "DTO com atributos para se atualizar um novo restaurante. Requer validação de dados informados, como nome, CNPJ, tipo de cozinha, endereço, capacidade, horário de abertura e fechamento")
      RestaurantInputDto restaurantInputDto);

}
