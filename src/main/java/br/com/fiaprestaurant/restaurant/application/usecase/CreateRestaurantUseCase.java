package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.application.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantOutputDto;
import br.com.fiaprestaurant.restaurant.application.validator.RestaurantAlreadyRegisteredByCnpjValidator;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRestaurantUseCase {

  private final RestaurantService restaurantService;
  private final RestaurantAlreadyRegisteredByCnpjValidator restaurantAlreadyRegisteredByCnpjValidator;

  public CreateRestaurantUseCase(RestaurantService restaurantService,
      RestaurantAlreadyRegisteredByCnpjValidator restaurantAlreadyRegisteredByCnpjValidator) {
    this.restaurantService = restaurantService;
    this.restaurantAlreadyRegisteredByCnpjValidator = restaurantAlreadyRegisteredByCnpjValidator;
  }

  @Transactional
  public RestaurantOutputDto execute(RestaurantInputDto restaurantInputDto) {
    restaurantAlreadyRegisteredByCnpjValidator.validate(restaurantInputDto.cnpj());
    var restaurant = RestaurantInputDto.toRestaurantFrom(restaurantInputDto);
    var restaurantSaved = restaurantService.save(restaurant);
    return RestaurantOutputDto.toRestaurantOutputDtoFrom(restaurantSaved);
  }
}
