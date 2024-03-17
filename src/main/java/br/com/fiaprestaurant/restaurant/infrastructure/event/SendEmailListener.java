package br.com.fiaprestaurant.restaurant.infrastructure.event;

import br.com.fiaprestaurant.restaurant.application.event.EmailEvent;
import br.com.fiaprestaurant.restaurant.infrastructure.mailer.MailerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener {

  private final MailerService mailerService;
  private final String emailAddressFrom;

  public SendEmailListener(MailerService mailerService,
      @Value("${spring.mail.username}") String emailAddressFrom) {
    this.mailerService = mailerService;
    this.emailAddressFrom = emailAddressFrom;
  }

  @Async
  @EventListener
  public void sendEmailTo(final EmailEvent emailEvent) {
    mailerService.send(emailAddressFrom, emailEvent.emailTitle(), emailEvent.emailBody(),
        emailEvent.emailDestination());
  }
}
