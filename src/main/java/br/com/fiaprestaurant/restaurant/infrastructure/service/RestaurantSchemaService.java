package br.com.fiaprestaurant.restaurant.infrastructure.service;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import org.springframework.stereotype.Service;

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
