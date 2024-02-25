package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantByIdInteractorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private UuidValidator uuidValidator;
  @InjectMocks
  private DeleteRestaurantByIdInteractor deleteRestaurantByIdInteractor;

  DeleteRestaurantByIdInteractorTest() {
  }

  @Test
  void shouldDeleteRestaurantById() {
    var restaurantSchema = RestaurantTestData.createRestaurantSchema();
    var id = restaurantSchema.getId().toString();

    assertThatCode(
        () -> deleteRestaurantByIdInteractor.execute(id))
        .doesNotThrowAnyException();
    verify(uuidValidator).validate(id);
    verify(restaurantGateway).deleteById(UUID.fromString(id));
  }

}