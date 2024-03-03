package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewOutputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantReviewsOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "RestaurantsReviewsApi", description = "API de cadastro de reviews")
public interface RestaurantsReviewsApi {

  @Operation(summary = "Avaliação de restaurantes",
      description = "Endpoint para avaliar restaurantes. O Necessário ID do restaurante, descrição da avaliação, nota e ID do usuário avaliador.",
      tags = "RestaurantsReviewsApi")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantReviewOutputDto.class))}),
      @ApiResponse(responseCode = "400", description = "bad request para validação do ID do restaurante, descrição da avaliação, nota e ID do usuário avaliador.",
          content = {@Content(schema = @Schema(hidden = true))})})
  RestaurantReviewOutputDto postRestaurantReview(
      @Parameter(description = "UUID do restaurante válido") String restaurantId,
      @Parameter(description = "UUID do usuário válido") String userId,
      RestaurantReviewInputDto restaurantReviewInputDto);

  @Operation(summary = "Lista de reviews de um restaurante",
      description = "Endpoint para recuperar uma lista de reviews de um restaurante selecionado",
      tags = {"RestaurantsReviewsApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantReviewOutputDto.class))})})
  List<RestaurantReviewsOutputDto> getAllReviewsByRestaurantId(
      @Parameter(description = "UUID do restaurante válido") String restaurantId);


}
