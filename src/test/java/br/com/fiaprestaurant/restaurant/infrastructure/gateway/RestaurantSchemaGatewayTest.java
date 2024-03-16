package br.com.fiaprestaurant.restaurant.infrastructure.gateway;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
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

  @Test
  void shouldFindRestaurantByIdRequired() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurantSchema.getId())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaGateway.findByIdRequired(restaurantSchema.getId());

    assertThat(restaurantFound).isNotNull();
    assertThat(restaurantFound.getId()).isNotNull().isEqualTo(restaurant.getId());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantIdDoesNotExist() {
    var restaurantSchema = createRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurantSchema.getId())).thenThrow(
        NoResultException.class);

    assertThatThrownBy(
        () -> restaurantSchemaGateway.findByIdRequired(restaurantSchema.getId()))
        .isInstanceOf(NoResultException.class);
  }

  @Test
  void shouldDeleteRestaurantById() {
    var restaurantSchema = createRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurantSchema.getId())).thenReturn(
        Optional.of(restaurantSchema));

    assertThatCode(() -> restaurantSchemaGateway.deleteById(restaurantSchema.getId()))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenDeleteRestaurantAndIdDoesNotExist() {
    var restaurantSchema = createRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurantSchema.getId())).thenThrow(
        NoResultException.class);

    assertThatThrownBy(
        () -> restaurantSchemaGateway.deleteById(restaurantSchema.getId()))
        .isInstanceOf(NoResultException.class);
  }

  @Test
  void shouldFindRestaurantSchemaByIdRequired() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurantSchema.getId())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaGateway.findByIdRequired(restaurantSchema.getId());

    assertThat(restaurantFound).isNotNull();
    assertThat(restaurantFound.getId()).isNotNull().isEqualTo(restaurant.getId());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundByIdRequired() {
    when(restaurantSchemaRepository.findById(DEFAULT_RESTAURANT_ID)).thenThrow(
        NoResultException.class);

    assertThatThrownBy(
            () -> restaurantSchemaGateway.findByIdRequired(DEFAULT_RESTAURANT_ID))
        .isInstanceOf(NoResultException.class);
  }

  @Test
  void shouldThrowExceptionWhenRestaurantSchemaWasNotFoundByIdRequired() {
    when(restaurantSchemaRepository.findById(DEFAULT_RESTAURANT_ID)).thenThrow(
        NoResultException.class);

    assertThatThrownBy(
            () -> restaurantSchemaGateway.findRestaurantSchemaByIdRequired(DEFAULT_RESTAURANT_ID))
        .isInstanceOf(NoResultException.class);
  }

}