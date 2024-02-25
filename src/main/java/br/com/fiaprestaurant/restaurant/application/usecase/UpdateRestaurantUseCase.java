package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;

public interface UpdateRestaurantUseCase {

  Restaurant execute(String id, Restaurant restaurant);

}
