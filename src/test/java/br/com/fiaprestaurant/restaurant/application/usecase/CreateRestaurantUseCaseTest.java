package br.com.fiaprestaurant.restaurant.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.entity.RestaurantBuilder;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

  @Mock
  private RestaurantService restaurantService;
  @InjectMocks
  private CreateRestaurantUseCase createRestaurantUseCase;

  @Test
  void shouldCreateNewRestaurant() {
    var restaurant = new RestaurantBuilder()
        .setId(UUID.randomUUID())
        .setName("Comida Boa").setCnpj("").setTypeOfCuisine("Brasileira").setStreet("Av Goiás")
        .setNumber("1000").setNeighborhood("Centro").setCity("Goiânia").setState("GO")
        .setPostalCode("74000000").setOpenAt("11:00").setCloseAt("15:00")
        .setCapacityOfPeople(200).createRestaurant();
    when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurant);

    var restaurantSaved = createRestaurantUseCase.execute(restaurant);

    assertThat(restaurantSaved).isNotNull();
    assertThat(restaurantSaved.getId()).isNotNull();
  }

}