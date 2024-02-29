package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_ALREADY_EXISTS_WITH_CNPJ;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.shared.infrastructure.exception.DuplicatedException;
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
    when(restaurantGateway.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.empty());

    assertThatCode(() -> restaurantSchemaAlreadyRegisteredByCnpjValidator.validate(
        restaurant.getCnpj().getCnpjValue())).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenRestaurantExistsWithCnpj() {
    var restaurant = RestaurantTestData.createRestaurant();
    when(restaurantGateway.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.of(restaurant));

    assertThatThrownBy(() -> restaurantSchemaAlreadyRegisteredByCnpjValidator.validate(
        restaurant.getCnpj().getCnpjValue()))
        .isInstanceOf(DuplicatedException.class)
        .hasMessage(RESTAURANT_ALREADY_EXISTS_WITH_CNPJ.formatted(restaurant.getCnpj().getCnpjValue()));
  }

}