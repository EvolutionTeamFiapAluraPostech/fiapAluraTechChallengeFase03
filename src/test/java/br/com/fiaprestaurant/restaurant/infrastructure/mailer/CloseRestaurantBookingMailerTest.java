package br.com.fiaprestaurant.restaurant.infrastructure.mailer;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;

import br.com.fiaprestaurant.restaurant.application.event.EmailEvent;
import br.com.fiaprestaurant.restaurant.application.event.EmailEventPublisher;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CloseRestaurantBookingMailerTest {

  @Mock
  private EmailEventPublisher emailEventPublisher;
  @Captor
  private ArgumentCaptor<EmailEvent> eventArgumentCaptor;
  @InjectMocks
  private CloseRestaurantBookingMailer closeRestaurantBookingMailer;

  @Test
  void shouldCloseRestaurantBookingMailer() {
    var user = createUser();
    var restaurant = createRestaurant();
    var bookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurant.getId().toString(), user.getId().toString(),
        RESTAURANT_BOOKING_DESCRIPTION, bookingDate.toString());

    assertThatCode(() -> closeRestaurantBookingMailer.createAndSendEmail(booking, restaurant,
        user)).doesNotThrowAnyException();

    verify(emailEventPublisher).publishEvent(eventArgumentCaptor.capture());
  }
}
