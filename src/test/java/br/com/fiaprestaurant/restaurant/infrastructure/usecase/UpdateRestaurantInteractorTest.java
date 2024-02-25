package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.infrastructure.validator.RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantInteractorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator;
  @InjectMocks
  private UpdateRestaurantInteractor updateRestaurantInteractor;

  @Test
  void shouldUpdateRestaurant() {
    var restaurant = RestaurantTestData.createRestaurant();
    var id = restaurant.getId().toString();
    var cnpj = restaurant.getCnpj().getCnpjValue();
    when(restaurantGateway.update(UUID.fromString(id), restaurant)).thenReturn(restaurant);

    var restaurantUpdated = updateRestaurantInteractor.execute(id, restaurant);

    assertThat(restaurantUpdated).isNotNull();
    verify(uuidValidator).validate(id);
    verify(restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator).validate(
        UUID.fromString(id), cnpj);
  }

}