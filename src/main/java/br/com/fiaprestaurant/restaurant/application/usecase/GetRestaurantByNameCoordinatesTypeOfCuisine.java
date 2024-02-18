package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.application.dto.RestaurantFilter;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantOutputDto;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetRestaurantByNameCoordinatesTypeOfCuisine {

  private final RestaurantService restaurantService;

  public GetRestaurantByNameCoordinatesTypeOfCuisine(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  public List<RestaurantOutputDto> execute(RestaurantFilter restaurantFilter) {

    var restaurants = restaurantService.queryByNameCoordinatesTypeOfCuisine(restaurantFilter.name(),
        restaurantFilter.latitude(), restaurantFilter.longitude(), restaurantFilter.typeOfCuisine());

    if (restaurants != null && !restaurants.isEmpty()) {
      return RestaurantOutputDto.toRestaurantsOutputDtoFrom(restaurants);
    }
    return new ArrayList<>();
  }
}
