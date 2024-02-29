package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_ALREADY_EXISTS_WITH_CNPJ;

import br.com.fiaprestaurant.restaurant.application.validator.RestaurantAlreadyRegisteredByCnpjValidator;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.shared.infrastructure.exception.DuplicatedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class RestaurantSchemaAlreadyRegisteredByCnpjValidator implements
    RestaurantAlreadyRegisteredByCnpjValidator {

  private final RestaurantGateway restaurantGateway;

  public RestaurantSchemaAlreadyRegisteredByCnpjValidator(RestaurantGateway restaurantGateway) {
    this.restaurantGateway = restaurantGateway;
  }

  @Override
  public void validate(String cnpj) {
    if (cnpj != null && !cnpj.isEmpty()) {
    var restaurant = restaurantGateway.findByCnpj(cnpj);
      if (restaurant.isPresent()) {
        throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "cnpj",
            RESTAURANT_ALREADY_EXISTS_WITH_CNPJ.formatted(cnpj)));
      }
    }
  }
}
