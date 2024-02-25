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
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetRestaurantByIdApiTest {

  private static final String URL_RESTAURANTS = "/restaurants/{id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetRestaurantByIdApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private RestaurantSchema createAndSaveRestaurant(String name, String cnpj, String typeOfCuisine,
      Double latitude, Double longitude, String openAt, String closeAt, Integer peopleCapacity,
      String street, String number, String neighborhood, String city, String state,
      String postalCode) {
    var restaurantSchema = createNewRestaurantSchema(name, cnpj, typeOfCuisine, latitude,
        longitude, openAt, closeAt, peopleCapacity, street, number, neighborhood, city, state,
        postalCode);
    return entityManager.merge(restaurantSchema);
  }

  @Test
  void shouldGetRestaurantById() throws Exception {
    var restaurantSchema = createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME,
        DEFAULT_RESTAURANT_VALID_CNPJ, DEFAULT_RESTAURANT_TYPE_OF_CUISINE,
        DEFAULT_RESTAURANT_LATITUDE, DEFAULT_RESTAURANT_LONGITUDE, DEFAULT_RESTAURANT_HOUR_OPEN_AT,
        DEFAULT_RESTAURANT_HOUR_CLOSE_AT, DEFAULT_RESTAURANT_PEOPLE_CAPACITY,
        DEFAULT_RESTAURANT_STREET, DEFAULT_RESTAURANT_NUMBER, DEFAULT_RESTAURANT_NEIGHBORHOOD,
        DEFAULT_RESTAURANT_CITY, DEFAULT_RESTAURANT_STATE, DEFAULT_RESTAURANT_POSTAL_CODE);

    var request = get(URL_RESTAURANTS, restaurantSchema.getId());
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", equalTo(restaurantSchema.getId().toString())))
        .andExpect(jsonPath("$.name", equalTo(restaurantSchema.getName())))
        .andExpect(jsonPath("$.cnpj", equalTo(restaurantSchema.getCnpj())));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a", "A"})
  void shouldThrowExceptionWhenIdIsInvalid(String id) throws Exception {
    var request = get(URL_RESTAURANTS, id);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldThrowExceptionWhenIdDoesNotExist() throws Exception {
    var request = get(URL_RESTAURANTS, UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

}
