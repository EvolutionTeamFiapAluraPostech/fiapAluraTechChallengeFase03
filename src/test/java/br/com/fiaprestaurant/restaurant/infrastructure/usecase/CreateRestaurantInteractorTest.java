package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.validator.RestaurantAlreadyRegisteredByCnpjValidator;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantInteractorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private RestaurantAlreadyRegisteredByCnpjValidator restaurantAlreadyRegisteredByCnpjValidator;
  @InjectMocks
  private CreateRestaurantInteractor createRestaurantInteractor;

  @Test
  void shouldCreateNewRestaurant() {
    var restaurantToSave = createRestaurant();
    when(restaurantGateway.save(any(Restaurant.class))).thenReturn(restaurantToSave);

    var restaurantOutputDto = createRestaurantInteractor.execute(restaurantToSave);

    assertThat(restaurantOutputDto).isNotNull();
    assertThat(restaurantOutputDto.getId()).isNotNull();
    verify(restaurantAlreadyRegisteredByCnpjValidator).validate(
        restaurantToSave.getCnpj().getCnpjValue());
  }

}