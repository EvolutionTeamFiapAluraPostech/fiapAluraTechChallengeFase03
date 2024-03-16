package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.RESTAURANT_BOOKING_DESCRIPTION;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.DEFAULT_RESTAURANT_ID_STRING;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.domain.valueobject.BookingState;
import br.com.fiaprestaurant.restaurant.infrastructure.validator.GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenInteractorTest {

  @Mock
  private BookingGateway bookingGateway;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private GetAllBookingSchemaByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator;
  @Mock
  private RestaurantGateway restaurantGateway;
  @InjectMocks
  private GetAllBookingByRestaurantIdAndStateAndBookingDateBetweenInteractor getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase;

  @Test
  void shouldGetAllBookingByFilterParameters() {
    var restaurantId = DEFAULT_RESTAURANT_ID_STRING;
    var bookingState = BookingState.RESERVED.name();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var endBookingDate = LocalDateTime.now().plusDays(2);
    var booking = createRestaurantBooking(restaurantId, DEFAULT_USER_UUID_FROM_STRING,
        RESTAURANT_BOOKING_DESCRIPTION, startBookingDate.toString());
    when(bookingGateway.findBookingByRestaurantIdAndBookingStateAndBookingDateBetween(
        DEFAULT_RESTAURANT_ID, bookingState, startBookingDate, endBookingDate)).thenReturn(
        List.of(booking));

    var bookingList = getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase.execute(
        restaurantId, bookingState, startBookingDate, endBookingDate);

    assertThat(bookingList).isNotEmpty().hasSize(1);
    assertThat(bookingList.get(0).getId()).isNotNull().isEqualTo(booking.getId());
    verify(uuidValidator).validate(restaurantId);
    verify(getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator).validate(
        restaurantId, bookingState, startBookingDate, endBookingDate);
    verify(restaurantGateway).findByIdRequired(DEFAULT_RESTAURANT_ID);
  }

  @Test
  void shouldReturnEmptyListWhenAllParametersWereEnteredAndNoResultWasFound() {
    var restaurantId = DEFAULT_RESTAURANT_ID_STRING;
    var bookingState = BookingState.RESERVED.name();
    var startBookingDate = LocalDateTime.now().plusDays(1);
    var endBookingDate = LocalDateTime.now().plusDays(2);
    when(bookingGateway.findBookingByRestaurantIdAndBookingStateAndBookingDateBetween(
        DEFAULT_RESTAURANT_ID, bookingState, startBookingDate, endBookingDate)).thenReturn(
        Collections.emptyList());

    var bookingList = getAllBookingByRestaurantIdAndStateAndBookingDateBetweenUseCase.execute(
        restaurantId, bookingState, startBookingDate, endBookingDate);

    assertThat(bookingList).isEmpty();
    verify(uuidValidator).validate(restaurantId);
    verify(getAllBookingByRestaurantIdAndStateAndBookingDateBetweenFieldsValidator).validate(
        restaurantId, bookingState, startBookingDate, endBookingDate);
    verify(restaurantGateway).findByIdRequired(DEFAULT_RESTAURANT_ID);
  }
}
