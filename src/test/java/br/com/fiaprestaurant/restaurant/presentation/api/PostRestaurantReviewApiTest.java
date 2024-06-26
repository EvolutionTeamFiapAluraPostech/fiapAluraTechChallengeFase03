package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.DEFAULT_RESTAURANT_REVIEW_SCORE;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantReviewTestData.createRestaurantReviewInputDto;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.ReviewSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.api.JsonUtil;
import br.com.fiaprestaurant.shared.util.StringUtil;
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

@IntegrationTest
@DatabaseTest
class PostRestaurantReviewApiTest {

  private static final String URL_RESTAURANTS_REVIEWS = "/restaurants/{restaurant-id}/reviews/{user-id}";
  private static final String REVIEW_JSON_TEMPLATE = """
      {
         "restaurantId": "%s",
         "description": "%s",
         "score": %s,
         "userId": "%s"
      }""";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostRestaurantReviewApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private RestaurantSchema createAndPersistRestaurantSchema() {
    var restaurantSchema = createNewRestaurantSchema();
    return entityManager.merge(restaurantSchema);
  }

  private UserSchema createAndPersistUserSchema() {
    var user = createNewUser();
    var userSchema = createNewUserSchema(user);
    return entityManager.merge(userSchema);
  }

  @Test
  void shouldCreateRestaurantReview() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());
    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
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

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a", "1"})
  void shouldReturnBadRequestWhenCreateRestaurantReviewWithAnInvalidRestaurantId(
      String restaurantIdParam) throws Exception {
    var userSchema = createAndPersistUserSchema();
    var requestBody = REVIEW_JSON_TEMPLATE.formatted(restaurantIdParam,
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());

    var request = post(URL_RESTAURANTS_REVIEWS,
        UUID.randomUUID(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantReviewWithAnNonExistentRestaurant()
      throws Exception {
    var restaurantId = UUID.randomUUID();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantId,
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());
    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantId.toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantReviewWithAnNonExistentUser() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userId = UUID.randomUUID();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userId);
    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantSchema.getId().toString(), userId.toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCreateRestaurantReviewWithInvalidDescription(String description)
      throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        description, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantReviewWithInvalidDescriptionLength()
      throws Exception {
    String description = StringUtil.generateStringLength(501);
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        description, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(ints = {-1, 11})
  void shouldReturnBadRequestWhenCreateRestaurantReviewWithInvalidScore(Integer score)
      throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, score, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantSchema.getId().toString(), userSchema.getId().toString())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenRequestWithDifferentRestaurantIdPathVariableAndRestaurantIdRequestBodyAttribute()
      throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        UUID.randomUUID(), userSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenRequestWithDifferentUserIdPathVariableAndUserIdRequestBodyAttribute()
      throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var userSchema = createAndPersistUserSchema();
    var restaurantReviewInputDto = createRestaurantReviewInputDto(restaurantSchema.getId(),
        DEFAULT_RESTAURANT_REVIEW_DESCRIPTION, DEFAULT_RESTAURANT_REVIEW_SCORE, userSchema.getId());

    var requestBody = JsonUtil.toJson(restaurantReviewInputDto);

    var request = post(URL_RESTAURANTS_REVIEWS,
        restaurantSchema.getId(), UUID.randomUUID())
        .contentType(APPLICATION_JSON)
        .content(requestBody);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

}