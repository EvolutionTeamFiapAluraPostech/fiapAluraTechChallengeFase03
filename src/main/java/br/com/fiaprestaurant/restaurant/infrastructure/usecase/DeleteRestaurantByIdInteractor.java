package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.DeleteRestaurantByIdUseCase;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteRestaurantByIdInteractor implements DeleteRestaurantByIdUseCase {

  private final RestaurantGateway restaurantGateway;
  private final UuidValidator uuidValidator;

  public DeleteRestaurantByIdInteractor(RestaurantGateway restaurantGateway,
      UuidValidator uuidValidator) {
    this.restaurantGateway = restaurantGateway;
    this.uuidValidator = uuidValidator;
  }

  @Transactional
  @Override
  public void execute(String id) {
    uuidValidator.validate(id);
    restaurantGateway.deleteById(UUID.fromString(id));
  }
}
