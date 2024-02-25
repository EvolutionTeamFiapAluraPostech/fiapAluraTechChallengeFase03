package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantSchemaGatewayTest {

  @Mock
  private RestaurantSchemaRepository restaurantSchemaRepository;
  @InjectMocks
  private RestaurantSchemaGateway restaurantSchemaGateway;

  @Test
  void shouldSaveRestaurant() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.save(any(RestaurantSchema.class))).thenReturn(restaurantSchema);

    var restaurantSaved = restaurantSchemaGateway.save(restaurant);

    assertThat(restaurantSaved).isNotNull();
    verify(restaurantSchemaRepository).save(any(RestaurantSchema.class));
  }

  @Test
  void shouldFindRestaurantById() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurant.getId())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaGateway.findById(restaurant.getId());

    assertThat(restaurantFound).isNotNull();
    verify(restaurantSchemaRepository).findById(restaurant.getId());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundById() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurant.getId())).thenReturn(
        Optional.empty());

    assertThatThrownBy(() -> restaurantSchemaGateway.findById(restaurant.getId())).isInstanceOf(
        NoResultException.class);
    verify(restaurantSchemaRepository).findById(restaurant.getId());
  }

  @Test
  void shouldFindRestaurantByCnpjRequired() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaGateway.findByCnpjRequired(
        restaurant.getCnpj().getCnpjValue());

    assertThat(restaurantFound).isNotNull();
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundByCnpjRequired() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.empty());

    assertThatThrownBy(
        () -> restaurantSchemaGateway.findByCnpjRequired(restaurant.getCnpj().getCnpjValue()))
        .isInstanceOf(NoResultException.class);
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldFindRestaurantByCnpj() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaGateway.findByCnpj(restaurant.getCnpj().getCnpjValue());

    assertThat(restaurantFound).isNotNull().isPresent();
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundByCnpj() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.empty());

    var restaurantFound = restaurantSchemaGateway.findByCnpj(restaurant.getCnpj().getCnpjValue());

    assertThat(restaurantFound).isNotPresent();
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldFindRestaurantByQueryNameCoordinatesTypeOfCuisine() {
    var restaurantSchema = createRestaurantSchema();
    var restaurantsSchema = List.of(restaurantSchema);
    when(restaurantSchemaRepository.queryByNameCoordinatesTypeOfCuisine(
        restaurantSchema.getName().toLowerCase(),
        restaurantSchema.getTypeOfCuisine().toLowerCase(),
        restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude()))
        .thenReturn(restaurantsSchema);

    var restaurants = restaurantSchemaGateway.queryByNameCoordinatesTypeOfCuisine(
        restaurantSchema.getName(), restaurantSchema.getTypeOfCuisine(),
        restaurantSchema.getLatitude(), restaurantSchema.getLongitude());

    assertThat(restaurants).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldThrowExceptionWhenAllParametersAreNullOnQueryByNameCoordinatesTypeOfCuisine() {
    assertThatThrownBy(() -> restaurantSchemaGateway.queryByNameCoordinatesTypeOfCuisine(
        null, null, null, null))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowExceptionWhenFindByQueryByNameCoordinatesTypeOfCuisineAreEmpty() {
    var restaurantSchema = createRestaurantSchema();
    when(restaurantSchemaRepository.queryByNameCoordinatesTypeOfCuisine(
        restaurantSchema.getName().toLowerCase(),
        restaurantSchema.getTypeOfCuisine().toLowerCase(),
        restaurantSchema.getLatitude(),
        restaurantSchema.getLongitude()))
        .thenReturn(Collections.emptyList());

    assertThatThrownBy(() -> restaurantSchemaGateway.queryByNameCoordinatesTypeOfCuisine(
        restaurantSchema.getName(), restaurantSchema.getTypeOfCuisine(),
        restaurantSchema.getLatitude(), restaurantSchema.getLongitude()))
        .isInstanceOf(NoResultException.class);
  }

  @Test
  void shouldUpdateRestaurant() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    restaurantSchema.setName(ALTERNATIVE_RESTAURANT_NAME);
    when(restaurantSchemaRepository.findById(restaurantSchema.getId())).thenReturn(
        Optional.of(restaurantSchema));
    when(restaurantSchemaRepository.save(restaurantSchema)).thenReturn(restaurantSchema);

    var restaurantUpdated = restaurantSchemaGateway.update(restaurantSchema.getId(), restaurant);

    assertThat(restaurantUpdated).isNotNull();
    assertThat(restaurantUpdated.getId()).isNotNull().isEqualTo(restaurant.getId());
    assertThat(restaurantUpdated.getName()).isNotNull().isEqualTo(restaurantSchema.getName());
  }


}