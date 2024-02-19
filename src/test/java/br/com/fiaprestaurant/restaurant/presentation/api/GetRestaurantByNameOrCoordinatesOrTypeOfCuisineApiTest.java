package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static br.com.fiaprestaurant.shared.testData.user.AuthenticateTestData.DEFAULT_USER_AUTHENTICATE_REQUEST_BODY;
import static br.com.fiaprestaurant.shared.testData.user.AuthenticateTestData.URL_AUTHENTICATE;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetRestaurantByNameOrCoordinatesOrTypeOfCuisineApiTest {

  private static final String URL_RESTAURANTS = "/restaurants/name-coordinates-typeofcuisine";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;
  @LocalServerPort
  private int port;
  private String token;

  @Autowired
  GetRestaurantByNameOrCoordinatesOrTypeOfCuisineApiTest(MockMvc mockMvc,
      EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private void createAndSaveRestaurant(String name, String cnpj, String typeOfCuisine,
      Double latitude, Double longitude) {
    var restaurantSchema = createNewRestaurantSchema(name, cnpj, typeOfCuisine, latitude,
        longitude);
    entityManager.merge(restaurantSchema);
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
  void shouldGetRestaurantByNameWithRestAssured() {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("name", DEFAULT_RESTAURANT_NAME)
        .when()
        .get(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.OK.value())
        .header("Content-Type", startsWith("application/json"))
        .body("id", Matchers.notNullValue());
  }

  @Test
  void shouldGetRestaurantByTypeOfCuisineWithRestAssured() {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("typeOfCuisine", ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE)
        .when()
        .get(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.OK.value())
        .header("Content-Type", startsWith("application/json"))
        .body("id", Matchers.notNullValue());
  }

  @Test
  void shouldGetRestaurantByCoordinatesWithRestAssured() {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE);

    given()
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .param("latitude", ALTERNATIVE_RESTAURANT_LATITUDE.toString())
        .param("longitude", ALTERNATIVE_RESTAURANT_LONGITUDE.toString())
        .when()
        .get(URL_RESTAURANTS)
        .then()
        .statusCode(HttpStatus.OK.value())
        .header("Content-Type", startsWith("application/json"))
        .body("id", Matchers.notNullValue());
  }

  @Test
  void shouldGetRestaurantByName() throws Exception {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE);

    var request = get(URL_RESTAURANTS)
        .param("name", DEFAULT_RESTAURANT_NAME);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }

  @Test
  void shouldGetRestaurantByTypeOfCuisine() throws Exception {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE);

    var request = get(URL_RESTAURANTS)
        .param("typeOfCuisine", ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }

  @Test
  void shouldGetRestaurantByCoordinates() throws Exception {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE);

    var request = get(URL_RESTAURANTS)
        .param("latitude", ALTERNATIVE_RESTAURANT_LATITUDE.toString())
        .param("longitude", ALTERNATIVE_RESTAURANT_LONGITUDE.toString());
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }
}
