package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_NAME;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.ALTERNATIVE_RESTAURANT_VALID_CNPJ;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_NAME;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetRestaurantApiTest {

  private static final String URL_RESTAURANTS = "/restaurants/name-coordinates-typeofcuisine";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetRestaurantApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private void createAndSaveRestaurant(String name, String cnpj, String typeOfCuisine) {
    var restaurantSchema = createNewRestaurantSchema(name, cnpj, typeOfCuisine);
    entityManager.merge(restaurantSchema);
  }

  @Test
  void shouldGetRestaurantByName() throws Exception {
    createAndSaveRestaurant(DEFAULT_RESTAURANT_NAME, DEFAULT_RESTAURANT_VALID_CNPJ,
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE);

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
        DEFAULT_RESTAURANT_TYPE_OF_CUISINE);
    createAndSaveRestaurant(ALTERNATIVE_RESTAURANT_NAME, ALTERNATIVE_RESTAURANT_VALID_CNPJ,
        ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE);

    var request = get(URL_RESTAURANTS)
        .param("typeOfCuisine", ALTERNATIVE_RESTAURANT_TYPE_OF_CUISINE);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()));
  }
}
