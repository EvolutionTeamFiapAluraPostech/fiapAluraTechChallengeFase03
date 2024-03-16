package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBookingSchema;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.BookingSchema;
import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenApiTest {

  private static final String URL_RESTAURANT_BOOKING = "/restaurants/{restaurant-id}/booking";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private UserSchema createAndPersistUserSchema() {
    var user = createNewUser();
    var userSchema = createUserSchema(user);
    return entityManager.merge(userSchema);
  }

  private RestaurantSchema createAndPersistRestaurantSchema() {
    var restaurantSchema = createNewRestaurantSchema();
    return entityManager.merge(restaurantSchema);
  }

  private BookingSchema createAndPersistBookingSchema(UserSchema userSchema,
      RestaurantSchema restaurantSchema) {
    var booking = createRestaurantBooking(restaurantSchema.getId().toString(),
        userSchema.getId().toString(), RESTAURANT_BOOKING_DESCRIPTION,
        LocalDateTime.now().plusDays(1).toString());
    var bookingSchema = createRestaurantBookingSchema(booking);
    return entityManager.merge(bookingSchema);
  }

  @Test
  void shouldGetRestaurantBookingByRestaurantId() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema);
    var startBookingDate = bookingSchema.getBookingDate().toString();
    var endBookingDate = bookingSchema.getBookingDate().plusDays(1).toString();

    var request = get(URL_RESTAURANT_BOOKING, restaurantSchema.getId())
        .param("restaurantId", restaurantSchema.getId().toString())
        .param("bookingState", BookingState.RESERVED.name())
        .param("startBookingDate", startBookingDate)
        .param("endBookingDate", endBookingDate)
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(1)))
        .andExpect(jsonPath("$[0].id", isUUID()))
        .andExpect(jsonPath("$[0].id", equalTo(bookingSchema.getId().toString())));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "a", "A1"})
  void shouldReturnBadRequestWhenGetBookingByInvalidRestaurantId(String restaurantId) throws Exception{
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema);
    var startBookingDate = bookingSchema.getBookingDate().toString();
    var endBookingDate = bookingSchema.getBookingDate().plusDays(1).toString();

    var request = get(URL_RESTAURANT_BOOKING, restaurantId)
        .param("restaurantId", restaurantId)
        .param("bookingState", BookingState.RESERVED.name())
        .param("startBookingDate", startBookingDate)
        .param("endBookingDate", endBookingDate)
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenGetBookingByNonExistentRestaurantId() throws Exception{
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema);
    var startBookingDate = bookingSchema.getBookingDate().toString();
    var endBookingDate = bookingSchema.getBookingDate().plusDays(1).toString();
    var restaurantId = UUID.randomUUID();

    var request = get(URL_RESTAURANT_BOOKING, restaurantId)
        .param("restaurantId", restaurantId.toString())
        .param("bookingState", BookingState.RESERVED.name())
        .param("startBookingDate", startBookingDate)
        .param("endBookingDate", endBookingDate)
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnOkAndEmptyListWhenBookingByExistentRestaurantIdWasNotFound() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema);
    var startBookingDate = bookingSchema.getBookingDate().plusDays(7).toString();
    var endBookingDate = bookingSchema.getBookingDate().plusDays(8).toString();

    var request = get(URL_RESTAURANT_BOOKING, restaurantSchema.getId())
        .param("restaurantId", restaurantSchema.getId().toString())
        .param("bookingState", BookingState.RESERVED.name())
        .param("startBookingDate", startBookingDate)
        .param("endBookingDate", endBookingDate)
        .contentType(APPLICATION_JSON);

    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(0)));
  }

}
