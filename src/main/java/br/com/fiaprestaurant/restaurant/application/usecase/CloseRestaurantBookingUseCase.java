package br.com.fiaprestaurant.restaurant.application.usecase;

public interface CloseRestaurantBookingUseCase {

  void execute(String restaurantId, String bookingId, String currentUsername);

}
