package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState.CANCELED;
import static br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState.CLOSED;
import static br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState.RESERVED;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBookingSchema;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
class PatchToCancelRestaurantBookingApiTest {

  private static final String URL_BOOKING = "/restaurants/{restaurant-id}/booking/{booking-id}/cancel";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PatchToCancelRestaurantBookingApiTest(MockMvc mockMvc, EntityManager entityManager) {
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
      RestaurantSchema restaurantSchema, BookingState bookingState) {
    var booking = createRestaurantBooking(restaurantSchema.getId().toString(),
        userSchema.getId().toString(), RESTAURANT_BOOKING_DESCRIPTION,
        LocalDateTime.now().plusDays(1).toString());
    var bookingSchema = createRestaurantBookingSchema(booking);
    bookingSchema.setBookingState(bookingState.name());
    return entityManager.merge(bookingSchema);
  }

  @Test
  void shouldCancelRestaurantBookingByRestaurantIdAndBookingId() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema, RESERVED);

    var request = patch(URL_BOOKING, restaurantSchema.getId(), bookingSchema.getId());
    mockMvc.perform(request)
        .andExpect(status().isAccepted());

    var bookingSchemaCanceled = entityManager.find(BookingSchema.class, bookingSchema.getId());
    assertThat(bookingSchemaCanceled).isNotNull();
    assertThat(bookingSchemaCanceled.getBookingState()).isNotNull().isEqualTo(CANCELED.name());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "a", "A1"})
  void shouldReturnBadRequestWhenCancelBookingWithInvalidRestaurantId(String restaurantId)
      throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema, RESERVED);

    var request = patch(URL_BOOKING, restaurantId, bookingSchema.getId());
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenCancelBookingWithNonExistentRestaurantId() throws Exception {
    var restaurantId = UUID.randomUUID();
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema, RESERVED);

    var request = patch(URL_BOOKING, restaurantId, bookingSchema.getId());
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "a", "A1"})
  void shouldReturnBadRequestWhenCancelBookingWithInvalidBookingId(String bookingId)
      throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();

    var request = patch(URL_BOOKING, restaurantSchema.getId(), bookingId);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnNotFoundWhenCancelBookingWithNonExistentBookingId() throws Exception {
    var restaurantSchema = createAndPersistRestaurantSchema();

    var request = patch(URL_BOOKING, restaurantSchema.getId(), UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenCancelBookingAlreadyCanceled() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema, CANCELED);

    var request = patch(URL_BOOKING, restaurantSchema.getId(), bookingSchema.getId());
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenCancelBookingAlreadyClosed() throws Exception {
    var userSchema = createAndPersistUserSchema();
    var restaurantSchema = createAndPersistRestaurantSchema();
    var bookingSchema = createAndPersistBookingSchema(userSchema, restaurantSchema, CLOSED);

    var request = patch(URL_BOOKING, restaurantSchema.getId(), bookingSchema.getId());
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

}
