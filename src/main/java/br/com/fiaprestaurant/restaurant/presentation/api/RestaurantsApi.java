package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantOutputDto;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "RestaurantsApi", description = "API de manutenção de restaurantes")
public interface RestaurantsApi {

  RestaurantOutputDto postRestaurant(RestaurantInputDto restaurantInputDto);

}
