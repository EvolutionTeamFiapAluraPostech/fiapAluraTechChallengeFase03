package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReviewInputDto;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.ReviewSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.api.JsonUtil;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import br.com.fiaprestaurant.shared.testData.user.UserTestData;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class PostRestaurantReviewApiTest {

  private static final String URL_RESTAURANTS_REVIEW = "/restaurants/{restaurant-id}/review/{user-id}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostRestaurantReviewApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private RestaurantSchema createAndPersistRestaurantSchema() {
    var restaurantSchema = RestaurantTestData.createNewRestaurantSchema();
    return entityManager.merge(restaurantSchema);
  }

  private UserSchema createAndPersistUserSchema() {
    var user = UserTestData.createNewUser();
    var userSchema = UserTestData.createNewUserSchema(user);
    return entityManager.merge(userSchema);
  }

  @Test
  void shouldCreateRestaurantReview() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());
    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEW,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    var mvcResult = mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();
    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var reviewFound = entityManager.find(ReviewSchema.class, UUID.fromString(id));
    assertThat(reviewFound).isNotNull();
    assertThat(reviewFound.getRestaurantSchema()).isNotNull();
    assertThat(reviewFound.getRestaurantSchema().getId()).isEqualTo(restaurantSchema.getId());
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantReviewWithInvalidRestaurant() throws Exception {
    var restaurantId = UUID.randomUUID();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantId,
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());
    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEW,
        restaurantId.toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantReviewWithInvalidUser() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userId = UUID.randomUUID();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userId);
    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEW,
        restaurantSchema.getId().toString(), userId.toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCreateRestaurantReviewWithInvalidDescription(String description) throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        description, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEW,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(ints = {-1, 11})
  void shouldReturnBadRequestWhenCreateRestaurantReviewWithInvalidScore(Integer score) throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, score, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEW,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}