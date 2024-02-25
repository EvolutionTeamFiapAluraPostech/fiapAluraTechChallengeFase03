package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.usecase.UpdateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.infrastructure.validator.RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateRestaurantInteractor implements UpdateRestaurantUseCase {

  private final RestaurantGateway restaurantGateway;
  private final UuidValidator uuidValidator;
  private final RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator;

  public UpdateRestaurantInteractor(RestaurantGateway restaurantGateway,
      UuidValidator uuidValidator,
      RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator) {
    this.restaurantGateway = restaurantGateway;
    this.uuidValidator = uuidValidator;
    this.restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator = restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator;
  }

  @Transactional
  @Override
  public Restaurant execute(String id, Restaurant restaurant) {
    uuidValidator.validate(id);
    restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator.validate(UUID.fromString(id),
        restaurant.getCnpj().getCnpjValue());
    return restaurantGateway.update(UUID.fromString(id), restaurant);
  }
}
