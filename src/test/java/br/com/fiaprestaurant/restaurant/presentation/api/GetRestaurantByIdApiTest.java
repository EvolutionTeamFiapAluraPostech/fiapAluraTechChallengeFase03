package br.com.fiaprestaurant.restaurant.presentation.api;

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

  private RestaurantSchema createAndSaveRestaurant() {
    var restaurantSchema = createNewRestaurantSchema();
    return entityManager.merge(restaurantSchema);
  }

  @Test
  void shouldGetRestaurantById() throws Exception {
    var restaurantSchema = createAndSaveRestaurant();

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
