package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.application.dto.RestaurantFilter;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantOutputDto;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import br.com.fiaprestaurant.restaurant.domain.valueobject.Coordinates;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetRestaurantByNameCoordinatesTypeOfCuisineUseCase {

  private final RestaurantService restaurantService;

  public GetRestaurantByNameCoordinatesTypeOfCuisineUseCase(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  public List<RestaurantOutputDto> execute(RestaurantFilter restaurantFilter) {
    var restaurants = restaurantService.queryByNameCoordinatesTypeOfCuisine(restaurantFilter.name(),
        restaurantFilter.latitude(), restaurantFilter.longitude(),
        restaurantFilter.typeOfCuisine());

    return RestaurantOutputDto.toRestaurantsOutputDtoFrom(restaurants);
  }
}
