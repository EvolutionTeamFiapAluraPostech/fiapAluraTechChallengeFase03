package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LATITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_LONGITUDE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_VALID_CNPJ;
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
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetRestaurantByNameOrCoordinatesOrTypeOfCuisineApiTest {

  private static final String URL_RESTAURANTS = "/restaurants/name-coordinates-typeofcuisine";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetRestaurantByNameOrCoordinatesOrTypeOfCuisineApiTest(MockMvc mockMvc,
      EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private void createAndSaveRestaurant(String name, String cnpj, String typeOfCuisine,
      Double latitude, Double longitude, String openAt, String closeAt, Integer peopleCapacity,
      String street, String number, String neighborhood, String city, String state,
      String postalCode) {
    var restaurantSchema = createNewRestaurantSchema(name, cnpj, typeOfCuisine, latitude,
        longitude, openAt, closeAt, peopleCapacity, street, number, neighborhood, city, state,
        postalCode);
    entityManager.merge(restaurantSchema);
  }

  @BeforeEach
  void setUp() {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE, DEFAULT_RESTAURANT_LATITUDE,
        DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE, ALTERNATIVE_RESTAURANT_LATITUDE,
        ALTERNATIVE_RESTAURANT_LONGITUDE, ALTERNATIVE_RESTAURANT_HOUR_OPEN_AT,
        ALTERNATIVE_RESTAURANT_HOUR_CLOSE_AT, ALTERNATIVE_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);
  }

  @Test
  void shouldGetRestaurantByName() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("name", DEFAULT_RESTAURANT_NAME);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantNameDoesNotExist() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("name", "Restaurante Chileno");
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldGetRestaurantByTypeOfCuisine() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("typeOfCuisine", ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantTypeOfCuisineDoesNotExist() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("typeOfCuisine", "Chilena");
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldGetRestaurantByCoordinates() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("latitude", ALTERNATIVE_RESTAURANT_LATITUDE.toString())
        .param("longitude", ALTERNATIVE_RESTAURANT_LONGITUDE.toString());
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }

  @Test
  void shouldThrowExceptionWhenRestaurantCoordinatesDoesNotExist() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("latitude", "-89")
        .param("longitude", "89");
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenCoordinateRestaurantSearchParameterIsInvalid() throws Exception {
    var request = get(URL_RESTAURANTS)
        .param("latitude", "")
        .param("longitude", "");
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenAllRestaurantSearchParametersAreInvalid() throws Exception {
    var request = get(URL_RESTAURANTS);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
