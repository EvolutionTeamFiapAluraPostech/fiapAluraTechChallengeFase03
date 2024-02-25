package br.com.fiaprestaurant.restaurant.application.validator;

import java.util.UUID;

public interface RestaurantAlreadyRegisteredByCnpjWithOtherIdValidator {

  void validate(UUID id, String cnpj);

}
