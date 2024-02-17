package br.com.fiaprestaurant.restaurant.infrastructure.service;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_NOT_FOUND_WITH_CNPJ;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_NOT_FOUND_WITH_ID;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.exception.NoResultException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class RestaurantSchemaService implements RestaurantService {

  private final RestaurantSchemaRepository restaurantSchemaRepository;

  public RestaurantSchemaService(RestaurantSchemaRepository restaurantSchemaRepository) {
    this.restaurantSchemaRepository = restaurantSchemaRepository;
  }

  @Override
  public Restaurant save(Restaurant restaurant) {
    var restaurantSchema = getRestaurantSchema(restaurant);
    var restaurantSchemaSaved = restaurantSchemaRepository.save(restaurantSchema);
    return restaurantSchemaSaved.createRestaurantFromRestaurantSchema();
  }

  @Override
  public Optional<Restaurant> findById(UUID id) {
    var restaurantSchema = restaurantSchemaRepository.findById(id).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "Restaurant ID",
            RESTAURANT_NOT_FOUND_WITH_ID.formatted(id))));
    return Optional.of(restaurantSchema.createRestaurantFromRestaurantSchema());
  }

  @Override
  public Optional<Restaurant> findByCnpjRequired(String cnpjValue) {
    var restaurantSchema = restaurantSchemaRepository.findByCnpj(cnpjValue).orElseThrow(
        () -> new NoResultException(
            new FieldError(this.getClass().getSimpleName(), "Restaurant CNPJ",
                RESTAURANT_NOT_FOUND_WITH_CNPJ.formatted(cnpjValue))));
    var restaurant = restaurantSchema.createRestaurantFromRestaurantSchema();
    return Optional.of(restaurant);
  }

  @Override
  public Optional<Restaurant> findByCnpj(String cnpjValue) {
    var restaurantSchema = restaurantSchemaRepository.findByCnpj(cnpjValue);
    return restaurantSchema.map(RestaurantSchema::createRestaurantFromRestaurantSchema);
  }

  private static RestaurantSchema getRestaurantSchema(Restaurant restaurant) {
    return RestaurantSchema.builder()
        .name(restaurant.getName())
        .cnpj(restaurant.getCnpj().getCnpjValue())
        .typeOfCuisine(restaurant.getTypeOfCuisine().getTypeOfCuisineDescription())
        .street(restaurant.getAddress().getStreet())
        .number(restaurant.getAddress().getNumber())
        .neighborhood(restaurant.getAddress().getNeighborhood())
        .city(restaurant.getAddress().getCity())
        .state(restaurant.getAddress().getState())
        .postalCode(restaurant.getAddress().getPostalCode())
        .openAt(restaurant.getOpenAt())
        .closeAt(restaurant.getCloseAt())
        .peopleCapacity(restaurant.getPeopleCapacity())
        .build();
  }

}
