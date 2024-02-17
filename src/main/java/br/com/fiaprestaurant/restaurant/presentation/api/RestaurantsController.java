package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController implements RestaurantsApi {

  private final CreateRestaurantUseCase createRestaurantUseCase;

  public RestaurantsController(CreateRestaurantUseCase createRestaurantUseCase) {
    this.createRestaurantUseCase = createRestaurantUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public RestaurantOutputDto postRestaurant(@RequestBody RestaurantInputDto restaurantInputDto) {
    return createRestaurantUseCase.execute(restaurantInputDto);
  }

}
