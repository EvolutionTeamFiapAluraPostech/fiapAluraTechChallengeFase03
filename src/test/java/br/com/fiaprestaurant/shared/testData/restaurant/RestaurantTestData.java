package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.entity.RestaurantBuilder;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantInputDto;
import java.util.UUID;

public final class RestaurantTestData {

  public static final String DEFAULT_RESTAURANT_NAME = "Comida Boa";
  public static final String DEFAULT_RESTAURANT_VALID_CNPJ = "69635854000140";
  public static final String DEFAULT_RESTAURANT_TYPE_OF_CUISINE = "Brasileira";
  public static final String DEFAULT_RESTAURANT_STREET = "Av Goiás";
  public static final String DEFAULT_RESTAURANT_NUMBER = "1000";
  public static final String DEFAULT_RESTAURANT_NEIGHBORHOOD = "Centro";
  public static final String DEFAULT_RESTAURANT_CITY = "Goiânia";
  public static final String DEFAULT_RESTAURANT_STATE = "GO";
  public static final String DEFAULT_RESTAURANT_POSTAL_CODE = "74000000";
  public static final String DEFAULT_RESTAURANT_HOUR_OPEN_AT = "11:00";
  public static final String DEFAULT_RESTAURANT_HOUR_CLOSE_AT = "15:00";
  public static final int DEFAULT_RESTAURANT_PEOPLE_CAPACITY = 200;

  public static RestaurantInputDto createRestaurantInputDto() {
    return new RestaurantInputDto(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);
  }

  public static Restaurant createNewRestaurant() {
    return new RestaurantBuilder()
        .setName(DEFAULT_RESTAURANT_NAME)
        .setCnpj(DEFAULT_RESTAURANT_VALID_CNPJ)
        .setTypeOfCuisine(DEFAULT_RESTAURANT_TYPE_OF_CUISINE)
        .setStreet(DEFAULT_RESTAURANT_STREET)
        .setNumber(DEFAULT_RESTAURANT_NUMBER)
        .setNeighborhood(DEFAULT_RESTAURANT_NEIGHBORHOOD)
        .setCity(DEFAULT_RESTAURANT_CITY)
        .setState(DEFAULT_RESTAURANT_STATE)
        .setPostalCode(DEFAULT_RESTAURANT_POSTAL_CODE)
        .setOpenAt(DEFAULT_RESTAURANT_HOUR_OPEN_AT)
        .setCloseAt(DEFAULT_RESTAURANT_HOUR_CLOSE_AT)
        .setPeopleCapacity(DEFAULT_RESTAURANT_PEOPLE_CAPACITY)
        .createRestaurant();
  }

  public static Restaurant createRestaurant() {
    return new RestaurantBuilder()
        .setId(UUID.randomUUID())
        .setName(DEFAULT_RESTAURANT_NAME)
        .setCnpj(DEFAULT_RESTAURANT_VALID_CNPJ)
        .setTypeOfCuisine(DEFAULT_RESTAURANT_TYPE_OF_CUISINE)
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
  }

  public static RestaurantSchema createRestaurantSchema() {
    return RestaurantSchema.builder()
        .id(UUID.randomUUID())
        .name(DEFAULT_RESTAURANT_NAME)
        .cnpj(DEFAULT_RESTAURANT_VALID_CNPJ)
        .typeOfCuisine(DEFAULT_RESTAURANT_TYPE_OF_CUISINE)
        .street(DEFAULT_RESTAURANT_STREET)
        .number(DEFAULT_RESTAURANT_NUMBER)
        .neighborhood(DEFAULT_RESTAURANT_NEIGHBORHOOD)
        .city(DEFAULT_RESTAURANT_CITY)
        .state(DEFAULT_RESTAURANT_STATE)
        .postalCode(DEFAULT_RESTAURANT_POSTAL_CODE)
        .openAt(DEFAULT_RESTAURANT_HOUR_OPEN_AT)
        .closeAt(DEFAULT_RESTAURANT_HOUR_CLOSE_AT)
        .peopleCapacity(DEFAULT_RESTAURANT_PEOPLE_CAPACITY)
        .build();
  }

}
