package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantByNameCoordinatesTypeOfCuisineUseCase;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantFilter;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantOutputDto;
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
    var restaurant = restaurantInputDto.toRestaurantFrom();
    var restaurantSaved = createRestaurantUseCase.execute(restaurant);
    return RestaurantOutputDto.toRestaurantOutputDtoFrom(restaurantSaved);
  }

  @GetMapping("/name-coordinates-typeofcuisine")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public List<RestaurantOutputDto> getRestaurantByNameOrCoordinatesOrTypeOfCuisine(
      RestaurantFilter restaurantFilter) {
    var restaurants = getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(
        restaurantFilter.name(),
        restaurantFilter.typeOfCuisine(), restaurantFilter.latitude(),
        restaurantFilter.longitude());
    return RestaurantOutputDto.toRestaurantsOutputDtoFrom(restaurants);
  }

}
