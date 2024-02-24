package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import java.util.List;

public interface GetRestaurantByNameCoordinatesTypeOfCuisineUseCase {

  List<Restaurant> execute(String name, String typeOfCuisine, Double latitude, Double longitude);
}
