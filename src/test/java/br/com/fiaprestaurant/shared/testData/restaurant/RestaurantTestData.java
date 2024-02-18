package br.com.fiaprestaurant.shared.testData.restaurant;

import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.restaurant.domain.entity.RestaurantBuilder;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.application.dto.RestaurantInputDto;
import java.util.UUID;

public final class RestaurantTestData {

  public static final String DEFAULT_RESTAURANT_NAME = "Comida Boa";
  public static final String DEFAULT_RESTAURANT_VALID_CNPJ = "38246267000136";
  public static final String DEFAULT_RESTAURANT_TYPE_OF_CUISINE = "Francesa";
  public static final Double DEFAULT_RESTAURANT_LATITUDE = -63.56391;
  public static final Double DEFAULT_RESTAURANT_LONGITUDE = -86.65239;
  public static final String DEFAULT_RESTAURANT_STREET = "Av Goiás";
  public static final String DEFAULT_RESTAURANT_NUMBER = "1000";
  public static final String DEFAULT_RESTAURANT_NEIGHBORHOOD = "Centro";
  public static final String DEFAULT_RESTAURANT_CITY = "Goiânia";
  public static final String DEFAULT_RESTAURANT_STATE = "GO";
  public static final String DEFAULT_RESTAURANT_POSTAL_CODE = "74000000";
  public static final String DEFAULT_RESTAURANT_HOUR_OPEN_AT = "11:00";
  public static final String DEFAULT_RESTAURANT_HOUR_CLOSE_AT = "15:00";
  public static final int DEFAULT_RESTAURANT_PEOPLE_CAPACITY = 200;
  public static final String ALTERNATIVE_RESTAURANT_NAME = "Sabor Argentino";
  public static final String ALTERNATIVE_RESTAURANT_VALID_CNPJ = "70813077000166";
  public static final String ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE = "Argentina";
  public static final Double ALTERNATIVE_RESTAURANT_LATITUDE = -33.563900;
  public static final Double ALTERNATIVE_RESTAURANT_LONGITUDE = -56.652390;


  public static RestaurantInputDto createRestaurantInputDto() {
    return new RestaurantInputDto(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
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

  public static RestaurantInputDto createRestaurantInputDtoWith(
      String name,
      String cnpj,
      String typeOfCuisine,
      Double latitude,
      Double longitude,
      String street,
      String number,
      String neighborhood,
      String city,
      String state,
      String postalCode,
      String openAt,
      String closeAt,
      Integer peopleCapacity) {
    return new RestaurantInputDto(name,
        cnpj,
        typeOfCuisine,
        latitude,
        longitude,
        street,
        number,
        neighborhood,
        city,
        state,
        postalCode,
        openAt,
        closeAt,
        peopleCapacity);
  }

  public static Restaurant createRestaurant() {
    return new RestaurantBuilder()
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
  }

  public static RestaurantSchema createRestaurantSchema() {
    var restaurantSchema = createNewRestaurantSchema(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE);
    restaurantSchema.setId(UUID.randomUUID());
    return restaurantSchema;
  }

  public static RestaurantSchema createNewRestaurantSchema(String name, String cnpj,
      String defaultRestaurantTypeOfCuisine, Double latitude, Double longitude) {
    return RestaurantSchema.builder()
        .name(name)
        .cnpj(cnpj)
        .typeOfCuisine(defaultRestaurantTypeOfCuisine)
        .latitude(latitude)
        .longitude(longitude)
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
