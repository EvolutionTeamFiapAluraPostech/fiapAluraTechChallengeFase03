package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRestaurantUseCase {

  private final RestaurantService restaurantService;

  public CreateRestaurantUseCase(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @Transactional
  public Restaurant execute(Restaurant restaurant) {
    return restaurantService.save(restaurant);
  }
}
