package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;

public interface GetRestaurantByIdUseCase {

  Restaurant execute(String id);

}
