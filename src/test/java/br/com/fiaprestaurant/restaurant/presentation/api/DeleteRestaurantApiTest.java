package br.com.fiaprestaurant.restaurant.presentation.api;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class DeleteRestaurantApiTest {

  private static final String URL_RESTAURANTS = "/restaurants/{id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  DeleteRestaurantApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private RestaurantSchema createAndSaveRestaurantSchema() {
    var restaurantSchema = RestaurantTestData.createNewRestaurantSchema();
    return entityManager.merge(restaurantSchema);
  }

  @Test
  void shouldDeleteRestaurant() throws Exception {
    var restaurantSchema = createAndSaveRestaurantSchema();

    var request = MockMvcRequestBuilders.delete(URL_RESTAURANTS, restaurantSchema.getId());
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNoContent());

    var restaurantSchemaFound = entityManager.find(RestaurantSchema.class, restaurantSchema.getId());
    assertThat(restaurantSchemaFound).isNotNull();
    assertThat(restaurantSchemaFound.getDeleted()).isTrue();
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "a"})
  void shouldThrowExceptionWhenRestaurantIsInvalid(String id) throws Exception {
    var request = MockMvcRequestBuilders.delete(URL_RESTAURANTS, id);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldThrowExceptionWhenRestaurantWasNotFoundById() throws Exception {
    var request = MockMvcRequestBuilders.delete(URL_RESTAURANTS, UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
