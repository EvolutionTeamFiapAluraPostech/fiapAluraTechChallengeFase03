package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantByIdUseCase;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GetRestaurantByIdInteractor implements GetRestaurantByIdUseCase {

  private final RestaurantGateway restaurantGateway;
  private final UuidValidator uuidValidator;

  public GetRestaurantByIdInteractor(RestaurantGateway restaurantGateway,
      UuidValidator uuidValidator) {
    this.restaurantGateway = restaurantGateway;
    this.uuidValidator = uuidValidator;
  }

  @Override
  public Restaurant execute(String id) {
    uuidValidator.validate(id);
    return restaurantGateway.findByIdRequired(UUID.fromString(id));
  }
}
