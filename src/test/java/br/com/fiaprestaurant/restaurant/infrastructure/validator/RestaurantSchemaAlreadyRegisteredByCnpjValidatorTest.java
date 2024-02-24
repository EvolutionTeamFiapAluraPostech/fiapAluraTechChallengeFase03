package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantSchemaAlreadyRegisteredByCnpjValidatorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @InjectMocks
  private RestaurantSchemaAlreadyRegisteredByCnpjValidator restaurantSchemaAlreadyRegisteredByCnpjValidator;

  @Test
  void shouldValidateWhenRestaurantNotExistWithCnpj() {
    var restaurant = RestaurantTestData.createRestaurant();
    when(restaurantGateway.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> restaurantSchemaAlreadyRegisteredByCnpjValidator.validate(
        restaurant.getCnpj().getCnpjValue()));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantExistsWithCnpj() {
    var restaurant = RestaurantTestData.createRestaurant();
    when(restaurantGateway.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(Optional.of(restaurant));

    assertThrows(DuplicatedException.class,
        () -> restaurantSchemaAlreadyRegisteredByCnpjValidator.validate(
            restaurant.getCnpj().getCnpjValue()));
  }

}