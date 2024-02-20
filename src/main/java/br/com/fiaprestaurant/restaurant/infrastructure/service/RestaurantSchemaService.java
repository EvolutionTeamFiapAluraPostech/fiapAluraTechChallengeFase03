package br.com.fiaprestaurant.restaurant.infrastructure.service;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_NOT_FOUND;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_NOT_FOUND_WITH_CNPJ;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.RESTAURANT_NOT_FOUND_WITH_ID;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.service.RestaurantService;
import br.com.fiaprestaurant.restaurant.infrastructure.repository.RestaurantSchemaRepository;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class RestaurantSchemaService implements RestaurantService {

  public static final String ENTER_AT_LEAST_ONE_PARAM_NAME_TYPE_OF_CUISINE_LATITUDE_AND_LONGITUDE = "Enter at least one param (name, type of cuisine, latitude and longitude).";
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

  @Override
  public List<Restaurant> queryByNameCoordinatesTypeOfCuisine(String name, Double latitude,
      Double longitude, String typeOfCuisine) {
    var nameParam = convertStringParamToTrimLowerCase(name);
    var typeOfCuisineParam = convertStringParamToTrimLowerCase(typeOfCuisine);
    var latitudeParam = convertDoubleParamToNullOrDoubleValue(latitude);
    var longitudeParam = convertDoubleParamToNullOrDoubleValue(longitude);
    validateQueryByNameCoordinatesTypeOfCuisineParams(nameParam, typeOfCuisineParam, latitudeParam,
        longitudeParam);

    var restaurantsSchema = restaurantSchemaRepository.queryByNameCoordinatesTypeOfCuisine(
        nameParam, typeOfCuisineParam, latitudeParam, longitudeParam);
    validateQueryByNameTypeOfCuisineCoordinatesResult(restaurantsSchema);

    return restaurantsSchema.stream().map(RestaurantSchema::createRestaurantFromRestaurantSchema)
        .toList();
  }

  private void validateQueryByNameTypeOfCuisineCoordinatesResult(List<RestaurantSchema> restaurantsSchema) {
    if (restaurantsSchema == null || restaurantsSchema.isEmpty()) {
      throw new NoResultException(
          new FieldError(this.getClass().getSimpleName(), "",
              RESTAURANT_NOT_FOUND));
    }
  }

  private void validateQueryByNameCoordinatesTypeOfCuisineParams(String nameParam,
      String typeOfCuisineParam, Double latitudeParam,
      Double longitudeParam) {
    if (nameParam == null && typeOfCuisineParam == null && latitudeParam == null
        && longitudeParam == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), "",
              ENTER_AT_LEAST_ONE_PARAM_NAME_TYPE_OF_CUISINE_LATITUDE_AND_LONGITUDE));
    }
  }

  private Double convertDoubleParamToNullOrDoubleValue(Double doubleParam) {
    return (doubleParam != null && doubleParam != 0) ? doubleParam : null;
  }

  private String convertStringParamToTrimLowerCase(String stringParam) {
    return stringParam != null ? stringParam.toLowerCase().trim() : null;
  }

  private static RestaurantSchema getRestaurantSchema(Restaurant restaurant) {
    return RestaurantSchema.builder()
        .name(restaurant.getName())
        .cnpj(restaurant.getCnpj().getCnpjValue())
        .typeOfCuisine(restaurant.getTypeOfCuisine().getTypeOfCuisineDescription())
        .latitude(restaurant.getAddress().getCoordinates().getLatitude())
        .longitude(restaurant.getAddress().getCoordinates().getLongitude())
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
