package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantByIdUseCase;
import br.com.fiaprestaurant.restaurant.application.usecase.GetRestaurantByNameCoordinatesTypeOfCuisineUseCase;
import br.com.fiaprestaurant.restaurant.application.usecase.UpdateRestaurantUseCase;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantFilter;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantOutputDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController implements RestaurantsApi {

  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final GetRestaurantByNameCoordinatesTypeOfCuisineUseCase getRestaurantByNameCoordinatesTypeOfCuisineUseCase;
  private final UpdateRestaurantUseCase updateRestaurantUseCase;
  private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;

  public RestaurantsController(CreateRestaurantUseCase createRestaurantUseCase,
      GetRestaurantByNameCoordinatesTypeOfCuisineUseCase getRestaurantByNameCoordinatesTypeOfCuisineUseCase,
      UpdateRestaurantUseCase updateRestaurantUseCase,
      GetRestaurantByIdUseCase getRestaurantByIdUseCase) {
    this.createRestaurantUseCase = createRestaurantUseCase;
    this.getRestaurantByNameCoordinatesTypeOfCuisineUseCase = getRestaurantByNameCoordinatesTypeOfCuisineUseCase;
    this.updateRestaurantUseCase = updateRestaurantUseCase;
    this.getRestaurantByIdUseCase = getRestaurantByIdUseCase;
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
        restaurantFilter.name(), restaurantFilter.typeOfCuisine(), restaurantFilter.latitude(),
        restaurantFilter.longitude());
    return RestaurantOutputDto.toRestaurantsOutputDtoFrom(restaurants);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @Override
  public RestaurantOutputDto putRestaurant(@PathVariable String id,
      @RequestBody RestaurantInputDto restaurantInputDto) {
    var restaurant = restaurantInputDto.toRestaurantFrom();
    var restaurantSaved = updateRestaurantUseCase.execute(id, restaurant);
    return RestaurantOutputDto.toRestaurantOutputDtoFrom(restaurantSaved);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public RestaurantOutputDto getRestaurantById(@PathVariable String id) {
    var restaurant = getRestaurantByIdUseCase.execute(id);
    return RestaurantOutputDto.toRestaurantOutputDtoFrom(restaurant);
  }

}
