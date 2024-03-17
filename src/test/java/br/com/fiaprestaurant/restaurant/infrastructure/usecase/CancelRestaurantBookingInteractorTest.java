package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.mailer.RestaurantBookingMailer;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelRestaurantBookingInteractorTest {

  @Mock
  private BookingGateway bookingGateway;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private UserGateway userGateway;
  @Mock
  private RestaurantBookingMailer restaurantBookingMailer;
  @InjectMocks
  private CancelRestaurantBookingInteractor cancelRestaurantBookingUseCase;

  @Test
  void shouldCancelBooking() {
    var user = createUser();
    var restaurant = RestaurantTestData.createRestaurant();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurant.getId().toString(), user.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, startBookingDate.toString());
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenReturn(restaurant);
    when(bookingGateway.findByIdRequired(booking.getId())).thenReturn(booking);
    when(userGateway.findByEmailRequired(user.getEmail().address())).thenReturn(user);

    assertThatCode(
        () -> cancelRestaurantBookingUseCase.execute(
            restaurant.getId().toString(), booking.getId().toString(), user.getEmail().address()))
        .doesNotThrowAnyException();
    verify(uuidValidator, times(2)).validate(any(String.class));
    verify(restaurantBookingMailer).createAndSendEmail(booking, restaurant, user);
  }

}
