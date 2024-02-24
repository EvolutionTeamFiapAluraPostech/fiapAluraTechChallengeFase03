package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.application.validator.RestaurantAlreadyRegisteredByCnpjValidator;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRestaurantInteractor implements CreateRestaurantUseCase {

  private final RestaurantGateway restaurantGateway;
  private final RestaurantAlreadyRegisteredByCnpjValidator restaurantAlreadyRegisteredByCnpjValidator;

  public CreateRestaurantInteractor(RestaurantGateway restaurantGateway,
      RestaurantAlreadyRegisteredByCnpjValidator restaurantAlreadyRegisteredByCnpjValidator) {
    this.restaurantGateway = restaurantGateway;
    this.restaurantAlreadyRegisteredByCnpjValidator = restaurantAlreadyRegisteredByCnpjValidator;
  }

  @Transactional
  public Restaurant execute(Restaurant restaurant) {
    restaurantAlreadyRegisteredByCnpjValidator.validate(restaurant.getCnpj().getCnpjValue());
    return restaurantGateway.save(restaurant);
  }
}
