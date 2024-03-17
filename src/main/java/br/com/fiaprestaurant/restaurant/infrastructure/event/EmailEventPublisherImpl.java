package br.com.fiaprestaurant.restaurant.infrastructure.event;

import br.com.fiaprestaurant.restaurant.application.event.EmailEvent;
import br.com.fiaprestaurant.restaurant.application.event.EmailEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EmailEventPublisherImpl implements EmailEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public EmailEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publishEvent(EmailEvent emailEvent) {
    applicationEventPublisher.publishEvent(emailEvent);
  }
}
