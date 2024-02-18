package br.com.fiaprestaurant.restaurant.domain.service;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantService {

  Restaurant save(Restaurant restaurant);

  Optional<Restaurant> findById(UUID id);

  Optional<Restaurant> findByCnpjRequired(String cnpjValue);

  Optional<Restaurant> findByCnpj(String cnpjValue);

  List<Restaurant> queryByNameCoordinatesTypeOfCuisine(String name, Double latitude,
      Double longitude, String typeOfCuisine);
}
