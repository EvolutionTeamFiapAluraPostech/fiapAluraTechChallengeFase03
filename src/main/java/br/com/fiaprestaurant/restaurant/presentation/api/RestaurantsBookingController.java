package br.com.fiaprestaurant.restaurant.presentation.api;

import br.com.fiaprestaurant.restaurant.application.usecase.CreateRestaurantBookingUseCase;
import br.com.fiaprestaurant.restaurant.application.usecase.GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingFilter;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingInputDto;
import br.com.fiaprestaurant.restaurant.presentation.dto.BookingOutputDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase;

  public RestaurantsBookingController(
      CreateRestaurantBookingUseCase createRestaurantBookingUseCase,
      GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase) {
    this.createRestaurantBookingUseCase = createRestaurantBookingUseCase;
    this.getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase = getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase;
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

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public List<BookingOutputDto> getAllBookingByRestaurantIdAndStateAndBookingDateBetween(
      @PathVariable(name = "restaurant-id") String restaurantId,
      BookingFilter filter) {
    var state = filter.bookingState();
    var startBookingDate = filter.startBookingDate();
    var endBookingDate = filter.endBookingDate();
    var booking = getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase.execute(
        restaurantId, state, startBookingDate, endBookingDate);
    return booking.stream().map(BookingOutputDto::from).toList();
  }

}
