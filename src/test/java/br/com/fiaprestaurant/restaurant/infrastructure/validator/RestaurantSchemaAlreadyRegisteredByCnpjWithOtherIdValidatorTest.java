package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_ALREADY_EXISTS_WITH_CNPJ_BUT_OTHER_ID;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_CITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_HOUR_CLOSE_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_HOUR_OPEN_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NEIGHBORHOOD;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NUMBER;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_PEOPLE_CAPACITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_POSTAL_CODE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_STATE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_STREET;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_VALID_CNPJ;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.domain.entity.RestaurantBuilder;
import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidatorTest {

  @Mock
  private RestaurantGateway restaurantGateway;
  @InjectMocks
  private RestaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator;

  @Test
  void shouldValidateWhenCnpjDoesNotExistInOtherRestaurant() {
    var id = UUID.randomUUID();
    var cnpj = DEFAULT_RESTAURANT_VALID_CNPJ;
    when(restaurantGateway.findByCnpj(cnpj)).thenReturn(Optional.empty());

    assertThatCode(
            () -> restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator.validate(id, cnpj))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenCnpjExistsInOtherRestaurant() {
    var restaurantFound = RestaurantTestData.createRestaurant();
    var restaurant = new RestaurantBuilder()
        .setId(UUID.randomUUID())
        .setName(DEFAULT_RESTAURANT_NAME)
        .setCnpj(DEFAULT_RESTAURANT_VALID_CNPJ)
        .setTypeOfCuisine(DEFAULT_RESTAURANT_TYPE_OF_CUISINE)
        .setLatitude(DEFAULT_RESTAURANT_LATITUDE)
        .setLongitude(DEFAULT_RESTAURANT_LONGITUDE)
        .setStreet(DEFAULT_RESTAURANT_STREET)
        .setNumber(DEFAULT_RESTAURANT_NUMBER)
        .setNeighborhood(DEFAULT_RESTAURANT_NEIGHBORHOOD)
        .setCity(DEFAULT_RESTAURANT_CITY)
        .setState(DEFAULT_RESTAURANT_STATE)
        .setPostalCode(DEFAULT_RESTAURANT_POSTAL_CODE)
        .setOpenAt(DEFAULT_RESTAURANT_HOUR_OPEN_AT)
        .setCloseAt(DEFAULT_RESTAURANT_HOUR_CLOSE_AT)
        .setPeopleCapacity(DEFAULT_RESTAURANT_PEOPLE_CAPACITY)
        .createRestaurantWithId();
    var id = restaurant.getId();
    var cnpj = restaurant.getCnpj().getCnpjValue();

    when(restaurantGateway.findByCnpj(cnpj)).thenReturn(Optional.of(restaurantFound));

    assertThatThrownBy(
            () -> restaurantSchemaAlreadyRegisteredByCnpjWithOtherIdValidator.validate(id, cnpj))
        .isInstanceOf(DuplicatedException.class)
        .hasMessage(RESTAURANT_ALREADY_EXISTS_WITH_CNPJ_BUT_OTHER_ID.formatted(cnpj));
  }

}