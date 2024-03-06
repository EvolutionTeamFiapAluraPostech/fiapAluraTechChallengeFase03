package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantBookingInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.RestaurantBookingOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/{restaurant-id}/booking")
public class RestaurantsBookingController implements RestaurantsBookingApi{

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public RestaurantBookingOutputDto postRestaurantBooking(String restaurantId,
      RestaurantBookingInputDto restaurantBookingInputDto) {
    return null;
  }
}
