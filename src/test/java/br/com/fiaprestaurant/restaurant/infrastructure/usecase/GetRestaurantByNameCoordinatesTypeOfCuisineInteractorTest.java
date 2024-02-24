package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetRestaurantByNameCoordinatesTypeOfCuisineInteractorTest {

  @Mock
  private RestaurantGateway restaurantGateway;

  @InjectMocks
  private GetRestaurantByNameCoordinatesTypeOfCuisineInteractor getRestaurantByNameCoordinatesTypeOfCuisineUseCase;

  @Test
  void shouldFindRestaurantByName() {
    var restaurant = createRestaurant();
    when(restaurantGateway.queryByNameCoordinatesTypeOfCuisine(restaurant.getName(), null, null,
        null)).thenReturn(List.of(restaurant));

    var restaurants = getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(
        restaurant.getName(), null, null, null);

    assertThat(restaurants).isNotEmpty().hasSize(1);
    assertThat(restaurants.get(0)).isNotNull();
    assertThat(restaurants.get(0).getId()).isNotNull();
    assertThat(restaurants.get(0).getName()).isEqualTo(restaurant.getName());
  }

  @Test
  void shouldFindRestaurantByCoordinates() {
    var restaurant = createRestaurant();
    when(restaurantGateway.queryByNameCoordinatesTypeOfCuisine(null,
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLatitude(), null)).thenReturn(
        List.of(restaurant));

    var restaurants = getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(null, null,
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLatitude());

    assertThat(restaurants).isNotEmpty().hasSize(1);
    assertThat(restaurants.get(0)).isNotNull();
    assertThat(restaurants.get(0).getId()).isNotNull();
    assertThat(restaurants.get(0).getName()).isEqualTo(restaurant.getName());
  }

  @Test
  void shouldFindRestaurantByTypeOfCuisine() {
    var restaurant = createRestaurant();
    when(restaurantGateway.queryByNameCoordinatesTypeOfCuisine(null, null, null,
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription())).thenReturn(
        List.of(restaurant));

    var restaurants = getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(null,
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription(), null, null);

    assertThat(restaurants).isNotEmpty().hasSize(1);
    assertThat(restaurants.get(0)).isNotNull();
    assertThat(restaurants.get(0).getId()).isNotNull();
    assertThat(restaurants.get(0).getName()).isEqualTo(restaurant.getName());
  }

  @Test
  void shouldFindRestaurantByNameAndCoordinatesAndTypeOfCuisine() {
    var restaurant = createRestaurant();
    when(restaurantGateway.queryByNameCoordinatesTypeOfCuisine(restaurant.getName(),
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLongitude(),
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription())).thenReturn(
        List.of(restaurant));

    var restaurants = getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(
        restaurant.getName(),
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription(),
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLongitude());

    assertThat(restaurants).isNotEmpty().hasSize(1);
    assertThat(restaurants.get(0)).isNotNull();
    assertThat(restaurants.get(0).getId()).isNotNull();
    assertThat(restaurants.get(0).getName()).isEqualTo(restaurant.getName());
  }

  @Test
  void shouldReturnEmptyListWhenAllParametersWasEnteredAndNoResultWasFound() {
    var restaurant = createRestaurant();
    when(restaurantGateway.queryByNameCoordinatesTypeOfCuisine(restaurant.getName(),
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLongitude(),
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription())).thenReturn(
        Collections.emptyList());

    var restaurants = getRestaurantByNameCoordinatesTypeOfCuisineUseCase.execute(
        restaurant.getName(),
        restaurant.getTypeOfCuisine().getTypeOfCuisineDescription(),
        restaurant.getAddress().getCoordinates().getLatitude(),
        restaurant.getAddress().getCoordinates().getLongitude());

    assertThat(restaurants).isEmpty();
  }

}