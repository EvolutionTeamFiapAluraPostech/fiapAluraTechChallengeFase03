package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.infrastructure.gateway.BookingSchemaGateway;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantBookingSchemaCapacityOfPeopleValidatorTest {

  @Mock
  private BookingSchemaGateway bookingSchemaGateway;
  @InjectMocks
  private RestaurantBookingSchemaCapacityOfPeopleValidator restaurantBookingSchemaCapacityOfPeopleValidator;

  @Test
  void shouldValidateRestaurantBookingSchemaCapacityOfPeople() {
    var restaurant = createRestaurant();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurant.getId().toString(),
        DEFAULT_USER_UUID_FROM_STRING, RESTAURANT_BOOKING_DESCRIPTION, startBookingDate.toString());

    assertThatCode(
        () -> restaurantBookingSchemaCapacityOfPeopleValidator.validate(restaurant,
            booking)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenRestaurantBookingSchemaCapacityOfPeopleIsNotValid() {
    var restaurant = createRestaurant();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var endBookingDate = startBookingDate.plusHours(1);
    var booking = createRestaurantBooking(restaurant.getId().toString(),
        DEFAULT_USER_UUID_FROM_STRING, RESTAURANT_BOOKING_DESCRIPTION, startBookingDate.toString());
    when(bookingSchemaGateway.restaurantBookingIsOverLimit(restaurant, startBookingDate,
        endBookingDate)).thenReturn(Boolean.TRUE);

    assertThatThrownBy(
        () -> restaurantBookingSchemaCapacityOfPeopleValidator.validate(restaurant, booking))
        .isInstanceOf(ValidatorException.class);
  }

}
