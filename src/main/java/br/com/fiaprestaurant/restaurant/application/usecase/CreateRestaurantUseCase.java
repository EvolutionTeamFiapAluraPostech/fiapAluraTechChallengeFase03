package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantOutputDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRestaurantUseCase {

  private final RestaurantService restaurantService;

  public CreateRestaurantUseCase(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @Transactional
  public RestaurantOutputDto execute(RestaurantInputDto restaurantInputDto) {
    var restaurant = RestaurantInputDto.toRestaurantFrom(restaurantInputDto);
    var restaurantSaved = restaurantService.save(restaurant);
    return RestaurantOutputDto.toRestaurantOutputDtoFrom(restaurantSaved);
  }
}
