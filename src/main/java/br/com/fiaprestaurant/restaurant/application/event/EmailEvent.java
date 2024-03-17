package br.com.fiaprestaurant.restaurant.application.event;

public record EmailEvent(String emailTitle, String emailBody, String emailDestination) {

}
