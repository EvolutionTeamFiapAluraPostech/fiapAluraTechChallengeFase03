package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantByNameCoordinatesTypeOfCuisineUseCase;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetRestaurantByNameCoordinatesTypeOfCuisineInteractor implements
    GetRestaurantByNameCoordinatesTypeOfCuisineUseCase {

  private final RestaurantGateway restaurantGateway;

  public GetRestaurantByNameCoordinatesTypeOfCuisineInteractor(
      RestaurantGateway restaurantGateway) {
    this.restaurantGateway = restaurantGateway;
  }

  @Override
  public List<Restaurant> execute(String name, String typeOfCuisine, Double latitude,
      Double longitude) {
    return restaurantGateway.queryByNameCoordinatesTypeOfCuisine(name, latitude, longitude,
        typeOfCuisine);
  }
}
