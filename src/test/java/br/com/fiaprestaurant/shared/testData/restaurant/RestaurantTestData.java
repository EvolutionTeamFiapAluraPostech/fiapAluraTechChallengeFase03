package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
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

  public static RestaurantSchema createRestaurantSchema() {
    return RestaurantSchema.builder()
        .id(UUID.randomUUID())
        .name(DEFAULT_RESTAURANT_NAME).cnpj(DEFAULT_RESTAURANT_VALID_CNPJ)
        .typeOfCuisine(DEFAULT_RESTAURANT_TYPE_OF_CUISINE).street(DEFAULT_RESTAURANT_STREET)
        .number(DEFAULT_RESTAURANT_NUMBER).neighborhood(DEFAULT_RESTAURANT_NEIGHBORHOOD)
        .city(DEFAULT_RESTAURANT_CITY).state(DEFAULT_RESTAURANT_STATE)
        .postalCode(DEFAULT_RESTAURANT_POSTAL_CODE).openAt(DEFAULT_RESTAURANT_HOUR_OPEN_AT)
        .closeAt(DEFAULT_RESTAURANT_HOUR_CLOSE_AT)
        .peopleCapacity(DEFAULT_RESTAURANT_PEOPLE_CAPACITY)
        .build();
  }

}
