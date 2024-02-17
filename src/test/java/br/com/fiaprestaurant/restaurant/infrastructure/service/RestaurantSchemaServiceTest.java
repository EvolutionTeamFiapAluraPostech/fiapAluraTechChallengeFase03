package br.com.fiaprestaurant.restaurant.infrastructure.service;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.exception.NoResultException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantSchemaServiceTest {

  @Mock
  private RestaurantSchemaRepository restaurantSchemaRepository;
  @InjectMocks
  private RestaurantSchemaService restaurantSchemaService;

  @Test
  void shouldSaveRestaurant() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.save(any(RestaurantSchema.class))).thenReturn(restaurantSchema);

    var restaurantSaved = restaurantSchemaService.save(restaurant);

    assertThat(restaurantSaved).isNotNull();
    verify(restaurantSchemaRepository).save(any(RestaurantSchema.class));
  }

  @Test
  void shouldFindRestaurantById() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurant.getId())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaService.findById(restaurant.getId());

    assertThat(restaurantFound).isNotNull();
    verify(restaurantSchemaRepository).findById(restaurant.getId());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundById() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findById(restaurant.getId())).thenReturn(
        Optional.empty());

    assertThrows(NoResultException.class,
        () -> restaurantSchemaService.findById(restaurant.getId()));
    verify(restaurantSchemaRepository).findById(restaurant.getId());
  }

  @Test
  void shouldFindRestaurantByCnpjRequired() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaService.findByCnpjRequired(restaurant.getCnpj().getCnpjValue());

    assertThat(restaurantFound).isNotNull();
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundByCnpjRequired() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.empty());

    assertThrows(NoResultException.class,
        () -> restaurantSchemaService.findByCnpjRequired(restaurant.getCnpj().getCnpjValue()));
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldFindRestaurantByCnpj() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.of(restaurantSchema));

    var restaurantFound = restaurantSchemaService.findByCnpj(restaurant.getCnpj().getCnpjValue());

    assertThat(restaurantFound).isNotNull().isPresent();
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundByCnpj() {
    var restaurantSchema = createRestaurantSchema();
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    when(restaurantSchemaRepository.findByCnpj(restaurant.getCnpj().getCnpjValue())).thenReturn(
        Optional.empty());

    var restaurantFound = restaurantSchemaService.findByCnpj(restaurant.getCnpj().getCnpjValue());

    assertThat(restaurantFound).isNotPresent();
    verify(restaurantSchemaRepository).findByCnpj(restaurant.getCnpj().getCnpjValue());
  }

}