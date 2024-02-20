package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.dto.RestaurantFilter;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantOutputDto;
import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantByNameCoordinatesTypeOfCuisineUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController implements RestaurantsApi {

  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final GetRestaurantByNameCoordinatesTypeOfCuisineUseCase getRestaurantByNameCoordinatesTypeOfCuisineUseCase;

  public RestaurantsController(CreateRestaurantUseCase createRestaurantUseCase,
      GetRestaurantByNameCoordinatesTypeOfCuisineUseCase getRestaurantByNameCoordinatesTypeOfCuisineUseCase) {
    this.createRestaurantUseCase = createRestaurantUseCase;
    this.getRestaurantByNameCoordinatesTypeOfCuisineUseCase = getRestaurantByNameCoordinatesTypeOfCuisineUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public RestaurantOutputDto postRestaurant(@RequestBody RestaurantInputDto restaurantInputDto) {
    return createRestaurantUseCase.execute(restaurantInputDto);
  }

  @GetMapping("/name-coordinates-typeofcuisine")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public List<RestaurantOutputDto> getRestaurantByNameOrCoordinatesOrTypeOfCuisine(
      RestaurantFilter restaurantFilter) {
    return getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(restaurantFilter);
  }

}
