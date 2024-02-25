package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetRestaurantByIdInteractorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private GetRestaurantByIdInteractor getRestaurantByIdInteractor;

  @Test
  void shouldGetRestaurantById() {
    var restaurant = createRestaurant();
    var id = restaurant.getId();
    when(restaurantGateway.findByIdRequired(id)).thenReturn(restaurant);

    var restaurantFound = getRestaurantByIdInteractor.execute(id.toString());

    assertThat(restaurantFound).isNotNull();
    assertThat(restaurantFound.getId()).isNotNull().isEqualTo(id);
    verify(uuidValidator).validate(id.toString());
  }

}