package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBookingInputDto;
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

import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.api.JsonUtil;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PostRestaurantBookingApiTest {

  private static final String URL_RESTAURANTS_BOOKING = "/restaurants/{restaurant-id}/booking";

  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostRestaurantBookingApiTest(MockMvc mockMvc, EntityManager entityManager) {
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

  private void createRestaurantBookingCollection(UserSchema userSchema,
      RestaurantSchema restaurantSchema) {
    var bookingDate = LocalDateTime.now().plusDays(1);
    for (int i = 1; i <= 51; i++) {
      bookingDate = bookingDate.plusSeconds(1);
      var bookingSchema = BookingSchema.builder().restaurantSchema(restaurantSchema)
          .userSchema(userSchema).description("Mesa longe da porta").bookingDate(bookingDate)
          .bookingState("RESERVED").build();
      entityManager.merge(bookingSchema);
    }
  }

  @Test
  void shouldCreateRestaurantBooking() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingDate = LocalDateTime.now().plusDays(1).toString();
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        restaurantSchema.getId().toString(), userSchema.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, restaurantSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var bookingFound = entityManager.find(BookingSchema.class, UUID.fromString(id));
    assertThat(bookingFound).isNotNull();
    assertThat(bookingFound.getId()).isNotNull();
    assertThat(bookingFound.getRestaurantSchema()).isNotNull();
    assertThat(bookingFound.getRestaurantSchema().getId()).isNotNull()
        .isEqualTo(restaurantSchema.getId());
    assertThat(bookingFound.getUserSchema()).isNotNull();
    assertThat(bookingFound.getUserSchema().getId()).isNotNull().isEqualTo(userSchema.getId());
    assertThat(bookingFound.getBookingDate()).isNotNull();
    assertThat(bookingFound.getBookingDate().toString()).hasToString(bookingDate);
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingWithAnInvalidRestaurantId()
      throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingDate = LocalDateTime.now().toString();
    var invalidRestaurantId = "code1";
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        invalidRestaurantId, userSchema.getId().toString(), RESTAURANT_BOOKING_DESCRIPTION,
        bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, restaurantSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantBookingWithAnNonExistentRestaurant()
      throws Exception {
    var userSchema = createAndPersistUserSchema();
    var bookingDate = LocalDateTime.now().toString();
    var invalidRestaurantId = UUID.randomUUID();
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        invalidRestaurantId.toString(), userSchema.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, invalidRestaurantId)
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantBookingWithAnNonExistentUser() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingDate = LocalDateTime.now().toString();
    var invalidUserId = UUID.randomUUID();
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        restaurantSchema.getId().toString(), invalidUserId.toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, restaurantSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingWithInvalidDate() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingDate = LocalDateTime.now().minusDays(1).toString();
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        restaurantSchema.getId().toString(), userSchema.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, restaurantSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingWithInvalidDateTime() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingDate = LocalDateTime.now().minusHours(1).toString();
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        restaurantSchema.getId().toString(), userSchema.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, restaurantSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingAtOverBookingCapacity() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    createRestaurantBookingCollection(userSchema, restaurantSchema);

    var bookingDate = LocalDateTime.now().plusDays(1).toString();
    var restaurantBookingInputDto = createRestaurantBookingInputDto(
        restaurantSchema.getId().toString(), userSchema.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate);
    var requestBody = JsonUtil.toJson(restaurantBookingInputDto);

    var request = post(URL_RESTAURANTS_BOOKING, restaurantSchema.getId())
        .contentType(APPLICATION_JSON)
        .content(requestBody);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

}
