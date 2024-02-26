package br.com.fiaprestaurant.restaurant.presentation.api;

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
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantInputDto;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurantInputDtoWith;
import static br.com.fiaprestaurant.shared.testData.user.AuthenticateTestData.DEFAULT_USER_AUTHENTICATE_REQUEST_BODY;
import static br.com.fiaprestaurant.shared.testData.user.AuthenticateTestData.URL_AUTHENTICATE;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.util.StringUtil;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@IntegrationTest
@DatabaseTest
class PostRestaurantApiTest {

  private static final String URL_RESTAURANTS = "/restaurants";
  public static final String RESTAURANT_CNPJ = "04638576000130";
  private final EntityManager entityManager;
  @LocalServerPort
  private int port;
  private String token;

  @Autowired
  PostRestaurantApiTest(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    var response = given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(DEFAULT_USER_AUTHENTICATE_REQUEST_BODY)
        .when()
        .post(URL_AUTHENTICATE);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().jsonPath()).isNotNull();
    assertThat(response.getBody().jsonPath().getString("token")).isNotNull();
    token = response.body().jsonPath().getString("token");
  }

  @Test
  void shouldCreateRestaurant() {
    var restaurantInputDto = createRestaurantInputDto();

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .header("Content-Type", startsWith("application/json"))
        .body("id", Matchers.notNullValue())
        .body("name", equalTo(restaurantInputDto.name()))
        .body("cnpj", equalTo(restaurantInputDto.cnpj()));

    var restaurantFound = entityManager
        .createQuery("select r from RestaurantSchema r where r.cnpj = :cnpj",
            RestaurantSchema.class)
        .setParameter("cnpj", restaurantInputDto.cnpj())
        .getSingleResult();
    assertThat(restaurantFound).isNotNull();
    assertThat(restaurantFound.getName()).isNotEmpty().isEqualTo(restaurantInputDto.name());
    assertThat(restaurantFound.getCnpj()).isNotEmpty().isEqualTo(restaurantInputDto.cnpj());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantCnpjAlreadyExists() {
    var restaurantInputDto = createRestaurantInputDtoWith(
        DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.CONFLICT.value());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantNameLengthIsBiggerThan100Characters() {
    var restaurantName = StringUtil.generateStringLength(101);
    var restaurantInputDto = createRestaurantInputDtoWith(restaurantName,
        RESTAURANT_CNPJ,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenRestaurantNameIsNullOrEmpty(String restaurantName) {
    var restaurantInputDto = createRestaurantInputDtoWith(restaurantName,
        RESTAURANT_CNPJ,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345678901234", "11111111111111", "22222222222222",
      "33333333333333", "44444444444444", "55555555555555", "66666666666666", "77777777777777",
      "88888888888888", "99999999999999", "00000000000000"})
  void shouldThrowExceptionWhenRestaurantCnpjIsInvalid(String cnpj) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        cnpj,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }


  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345", "a0:00", "10:a0"})
  void shouldThrowExceptionWhenRestaurantOpenAtIsInvalid(String openAt) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        openAt,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1", "12345", "a0:00", "10:a0"})
  void shouldThrowExceptionWhenRestaurantCloseAtIsInvalid(String closeAt) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
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
        closeAt,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 0})
  void shouldThrowExceptionWhenRestaurantPeopleCapacitySmallerThan0(Integer peopleCapacity) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
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
        peopleCapacity);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCreateRestaurantTypeOfCuisineIsNullOrEmpty(String typeOfCuisine) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        typeOfCuisine,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantTypeOfCuisineLengthIsBiggerThan50Characters() {
    var typeOfCuisine = StringUtil.generateStringLength(51);
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        typeOfCuisine,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCreateRestaurantStreetIsNullOrEmpty(String street) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        street,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantStreetLengthIsBiggerThan255Characters() {
    var street = StringUtil.generateStringLength(256);
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        street,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCreateRestaurantNumberIsNullOrEmpty(String number) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        number,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantNumberLengthIsBiggerThan100Characters() {
    var number = StringUtil.generateStringLength(101);
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        number,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCreateRestaurantNeighborhoodIsNullOrEmpty(String neighborhood) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        neighborhood,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantNeighborhoodLengthIsBiggerThan100Characters() {
    var neighborhood = StringUtil.generateStringLength(101);
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        neighborhood,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenCreateRestaurantCityIsNullOrEmpty(String city) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        city,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantCityLengthIsBiggerThan100Characters() {
    var city = StringUtil.generateStringLength(101);
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        city,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantLatitudeIsNullOrEmpty() {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        null,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @ValueSource(doubles = {-100d, 100d})
  void shouldThrowExceptionWhenCreateRestaurantLatitudeIsLowerThan90NegativeOrIsBiggerThan90Positive(
      Double latitude) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        latitude,
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

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantLongitudeIsNullOrEmpty() {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        null,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

  @ParameterizedTest
  @ValueSource(doubles = {-100d, 100d})
  void shouldThrowExceptionWhenCreateRestaurantLongitudeIsLowerThan90NegativeOrIsBiggerThan90Positive(
      Double longitude) {
    var restaurantInputDto = createRestaurantInputDtoWith(DEFAULT_RESTAURANT_NAME,
        RESTAURANT_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE,
        longitude,
        DEFAULT_RESTAURANT_STREET,
        DEFAULT_RESTAURANT_NUMBER,
        DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY,
        DEFAULT_RESTAURANT_STATE,
        DEFAULT_RESTAURANT_POSTAL_CODE,
        DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT,
        DEFAULT_RESTAURANT_PEOPLE_CAPACITY);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurantInputDto)
        .when()
        .post(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .header("Content-Type", startsWith("application/json"));
  }

}