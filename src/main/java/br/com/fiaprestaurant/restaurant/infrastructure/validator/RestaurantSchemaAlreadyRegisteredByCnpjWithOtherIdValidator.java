package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_ALREADY_EXISTS_WITH_CNPJ_BUT_OTHER_ID;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.validator.RestaurantAlreadyRegisteredByCnpjWithOtherIdValidator;
import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator implements
    RestaurantAlreadyRegisteredByCnpjWithOtherIdValidator {

  private final RestaurantGateway restaurantGateway;

  public RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator(RestaurantGateway restaurantGateway) {
    this.restaurantGateway = restaurantGateway;
  }

  @Override
  public void validate(UUID id, String cnpj) {
    var restaurant = restaurantGateway.findByCnpj(cnpj);
    if (restaurant.isPresent() && !restaurant.get().getId().equals(id)) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "cnpj",
          RESTAURANT_ALREADY_EXISTS_WITH_CNPJ_BUT_OTHER_ID.formatted(cnpj)));
    }
  }
}
