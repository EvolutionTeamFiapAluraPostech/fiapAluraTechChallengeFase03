package br.com.fiaprestaurant.restaurant.presentation.api;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createNewRestaurantSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;

import br.com.fiaprestaurant.restaurant.infrastructure.schema.RestaurantSchema;
import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PostRestaurantBookingApiTest {

  private static final String URL_RESTAURANTS_BOOKING = "/restaurants/{restaurant-id}/booking";
  private static final String RESTAURANT_BOOKING_JSON_TEMPLATE = """
      {
         "restaurantId": "%s",
         "userId": "%s"
      }""";

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

  @Test
  void shouldCreateRestaurantBooking() {
    Assertions.fail();
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingWithAnInvalidRestaurantId() {
    Assertions.fail();
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantBookingWithAnNonExistentRestaurant() {
    Assertions.fail();
  }

  @Test
  void shouldReturnNotFoundWhenCreateRestaurantBookingWithAnNonExistentUser() {
    Assertions.fail();
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingWithInvalidDate() {
    Assertions.fail();
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingWithInvalidDateTime() {
    Assertions.fail();
  }

  @Test
  void shouldReturnBadRequestWhenCreateRestaurantBookingAtOverBookingCapacity() {
    Assertions.fail();
  }

}
