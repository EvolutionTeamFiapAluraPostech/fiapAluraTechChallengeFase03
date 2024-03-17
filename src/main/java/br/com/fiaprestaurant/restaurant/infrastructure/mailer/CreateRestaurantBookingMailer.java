package br.com.fiaprestaurant.restaurant.infrastructure.mailer;

import br.com.fiaprestaurant.restaurant.application.event.EmailEvent;
import br.com.fiaprestaurant.restaurant.application.event.EmailEventPublisher;
import br.com.fiaprestaurant.restaurant.application.mailer.RestaurantBookingMailer;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.restaurant.domain.entity.Restaurant;
import br.com.fiaprestaurant.user.domain.entity.User;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component("createRestaurantBookingMailer")
public class CreateRestaurantBookingMailer implements RestaurantBookingMailer {

  private final EmailEventPublisher emailEventPublisher;

  public CreateRestaurantBookingMailer(EmailEventPublisher emailEventPublisher) {
    this.emailEventPublisher = emailEventPublisher;
  }

  @Override
  public void createAndSendEmail(Booking booking, Restaurant restaurant, User user) {
    var emailTitle = "Booking made at %s restaurant ".formatted(restaurant.getName());
    var emailBody = createEmailBody(booking, restaurant, user);
    var emailDestination = user.getEmail().address();
    emailEventPublisher.publishEvent(new EmailEvent(emailTitle, emailBody, emailDestination));
  }

  private String createEmailBody(Booking booking, Restaurant restaurant, User user) {
    var formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    var bookingDateTime = formatDate.format(booking.getBookingDate());
    return """
        Hello %s, your reservation at the %s restaurant, for %s, was made successfully!
        """.formatted(user.getName(), restaurant.getName(), bookingDateTime);
  }
}
