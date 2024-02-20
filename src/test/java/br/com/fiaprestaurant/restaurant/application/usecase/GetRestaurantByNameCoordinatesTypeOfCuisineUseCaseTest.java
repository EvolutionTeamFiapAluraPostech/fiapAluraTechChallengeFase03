package br.com.fiaprestaurant.restaurant.application.usecase;

import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetRestaurantByNameCoordinatesTypeOfCuisineUseCaseTest {

  @Mock
  private RestaurantService restaurantService;

  @InjectMocks
  private GetRestaurantByNameCoordinatesTypeOfCuisineUseCase getRestaurantByNameCoordinatesTypeOfCuisineUseCase;

  @Test
  void shouldFindRestaurantByName() {

  }

}