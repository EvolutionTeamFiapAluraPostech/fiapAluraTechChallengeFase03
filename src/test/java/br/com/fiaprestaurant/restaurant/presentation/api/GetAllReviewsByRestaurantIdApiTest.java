package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createNewRestaurantReviewSchema;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReview;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.ReviewSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import br.com.fiaprestaurant.shared.testData.user.UserTestData;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetAllReviewsByRestaurantIdApiTest {

  private static final String URL_RESTAURANTS_REVIEWS = "/restaurants/{restaurant-id}/reviews";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetAllReviewsByRestaurantIdApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private RestaurantSchema createAndPersistRestaurantSchema() {
    var restaurantSchema = RestaurantTestData.createNewRestaurantSchema();
    return entityManager.merge(restaurantSchema);
  }

  private UserSchema createAndPersistUserSchema() {
    var user = UserTestData.createNewUser();
    var userSchema = UserTestData.createUserSchema(user);
    return entityManager.merge(userSchema);
  }

  private ReviewSchema createAndPersistReviewSchema(UserSchema userSchema,
      RestaurantSchema restaurantSchema) {
    var review = createRestaurantReview(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());
    var reviewSchema = createNewRestaurantReviewSchema(review);
    return entityManager.merge(reviewSchema);
  }

  @Test
  void shouldGetRestaurantReviewByRestaurantId() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var reviewSchema = createAndPersistReviewSchema(userSchema, restaurantSchema);

    var request = get(URL_RESTAURANTS_REVIEWS, restaurantSchema.getId())
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()))
        .andExpect(jsonPath("$[0].id", equalTo(reviewSchema.getId().toString())));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "a"})
  void shouldReturnBadRequestWhenGetReviewByInvalidRestaurantId(String restaurantId)
      throws Exception {
    var request = get(URL_RESTAURANTS_REVIEWS, restaurantId)
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenGetReviewByNonExistentRestaurantId()
      throws Exception {
    var request = get(URL_RESTAURANTS_REVIEWS, UUID.randomUUID())
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnOkAndEmptyListWhenReviewByExistentRestaurantIdWasNotFound() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var request = get(URL_RESTAURANTS_REVIEWS, restaurantSchema.getId())
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(0)));
  }

}
