package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantBookingUseCase;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/{restaurant-id}/booking")
public class RestaurantsBookingController implements RestaurantsBookingApi {

  private final CreateRestaurantBookingUseCase createRestaurantBookingUseCase;

  public RestaurantsBookingController(
      CreateRestaurantBookingUseCase createRestaurantBookingUseCase) {
    this.createRestaurantBookingUseCase = createRestaurantBookingUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public BookingOutputDto postRestaurantBooking(
      @PathVariable(name = "restaurant-id") String restaurantId,
      @RequestBody BookingInputDto bookingInputDto) {
    var booking = bookingInputDto.toBookingfrom();
    var bookingSaved = createRestaurantBookingUseCase.execute(restaurantId, booking);
    return BookingOutputDto.from(bookingSaved);
  }
}
