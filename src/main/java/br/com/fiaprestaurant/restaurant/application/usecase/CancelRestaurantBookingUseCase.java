package br.com.fiaprestaurant.restaurant.application.usecase;

public interface CancelRestaurantBookingUseCase {

  void execute(String restaurantId, String bookingId, String currentUsername);

}
