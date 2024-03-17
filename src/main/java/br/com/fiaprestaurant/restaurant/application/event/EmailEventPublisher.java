package br.com.fiaprestaurant.restaurant.application.event;

public interface EmailEventPublisher {

  void publishEvent(final EmailEvent emailEvent);

}
