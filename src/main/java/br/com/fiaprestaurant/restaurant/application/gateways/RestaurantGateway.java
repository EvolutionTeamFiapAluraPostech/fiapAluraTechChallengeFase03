package br.com.fiaprestaurant.restaurant.application.gateways;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantGateway {

  Restaurant save(Restaurant restaurant);

  Restaurant update(UUID id, Restaurant restaurant);

  Optional<Restaurant> findById(UUID id);

  Optional<Restaurant> findByCnpjRequired(String cnpjValue);

  Optional<Restaurant> findByCnpj(String cnpjValue);

  List<Restaurant> queryByNameCoordinatesTypeOfCuisine(String name, String typeOfCuisine,
      Double latitude, Double longitude);
}
