package br.com.fiaprestaurant.restaurant.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantBookingTestData.createRestaurantBooking;
import static br.com.fiaprestaurant.shared.testData.restaurant.RestaurantTestData.createRestaurant;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_UUID_FROM_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.restaurant.application.gateways.BookingGateway;
import br.com.fiaprestaurant.restaurant.application.gateways.RestaurantGateway;
import br.com.fiaprestaurant.restaurant.application.mailer.CreateRestaurantBookingMailer;
import br.com.fiaprestaurant.restaurant.application.validator.RestaurantBookingCapacityOfPeopleValidator;
import br.com.fiaprestaurant.restaurant.domain.entity.Booking;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.shared.testData.user.UserTestData;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantBookingInteractorTest {

  @Mock
  private BookingGateway bookingGateway;
  @Mock
  private RestaurantGateway restaurantGateway;
  @Mock
  private UserGateway userGateway;
  @Mock
  private UuidValidator uuidValidator;
  @Mock
  private RestaurantBookingCapacityOfPeopleValidator restaurantBookingCapacityOfPeopleValidator;
  @Mock
  private CreateRestaurantBookingMailer createRestaurantBookingMailer;
  @InjectMocks
  private CreateRestaurantBookingInteractor createRestaurantBookingInteractor;

  @Test
  void shouldCreateRestaurantBooking() {
    var restaurant = createRestaurant();
    var restaurantId = restaurant.getId().toString();
    var user = UserTestData.createUser();
    var userId = user.getId();
    var bookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurantId, userId.toString(), "",
        bookingDate.toString());
    when(userGateway.findUserByIdRequired(userId)).thenReturn(user);
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenReturn(restaurant);
    when(bookingGateway.save(Mockito.any(Booking.class))).thenReturn(booking);

    var bookingSaved = createRestaurantBookingInteractor.execute(restaurant.getId().toString(),
        booking);

    assertThat(bookingSaved).isNotNull();
    assertThat(bookingSaved.getId()).isNotNull().isEqualTo(booking.getId());
    verify(uuidValidator).validate(restaurantId);
    verify(userGateway).findUserByIdRequired(userId);
    verify(restaurantBookingCapacityOfPeopleValidator).validate(restaurant, booking);
    verify(bookingGateway).save(booking);
    verify(createRestaurantBookingMailer).createAndSendEmail(booking, restaurant, user);
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantBookingWithInvalidRestaurant() {
    var restaurant = createRestaurant();
    var restaurantId = restaurant.getId();
    var restaurantIdFromString = restaurantId.toString();
    var userId = DEFAULT_USER_UUID_FROM_STRING;
    var bookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurantIdFromString, userId, "",
        bookingDate.toString());
    when(restaurantGateway.findByIdRequired(restaurantId)).thenThrow(NoResultException.class);

    assertThatThrownBy(
        () -> createRestaurantBookingInteractor.execute(restaurantIdFromString, booking));

    verify(uuidValidator).validate(restaurantIdFromString);
    verify(userGateway).findUserByIdRequired(UUID.fromString(userId));
    verify(restaurantBookingCapacityOfPeopleValidator, never()).validate(restaurant, booking);
    verify(bookingGateway, never()).save(booking);
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantBookingWithInvalidUser() {
    var restaurant = createRestaurant();
    var restaurantId = restaurant.getId().toString();
    var userId = DEFAULT_USER_UUID_FROM_STRING;
    var bookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurantId, userId, "", bookingDate.toString());
    when(userGateway.findUserByIdRequired(DEFAULT_USER_UUID)).thenThrow(NoResultException.class);

    assertThatThrownBy(
        () -> createRestaurantBookingInteractor.execute(restaurant.getId().toString(), booking));

    verify(uuidValidator).validate(restaurantId);
    verify(userGateway).findUserByIdRequired(UUID.fromString(userId));
    verify(restaurantGateway, never()).findByIdRequired(restaurant.getId());
    verify(restaurantBookingCapacityOfPeopleValidator, never()).validate(restaurant, booking);
    verify(bookingGateway, never()).save(booking);
  }

  @Test
  void shouldThrowExceptionWhenCreateRestaurantBookingWithOverLimitOfPeopleCapacity() {
    var restaurant = createRestaurant();
    var restaurantId = restaurant.getId().toString();
    var userId = DEFAULT_USER_UUID_FROM_STRING;
    var bookingDate = LocalDateTime.now().plusDays(1);
    var booking = createRestaurantBooking(restaurantId, userId, "", bookingDate.toString());
    when(restaurantGateway.findByIdRequired(restaurant.getId())).thenReturn(restaurant);
    doThrow(ValidatorException.class).when(restaurantBookingCapacityOfPeopleValidator)
        .validate(restaurant, booking);

    assertThatThrownBy(
        () -> createRestaurantBookingInteractor.execute(restaurant.getId().toString(), booking));

    verify(uuidValidator).validate(restaurantId);
    verify(userGateway).findUserByIdRequired(UUID.fromString(userId));
    verify(restaurantBookingCapacityOfPeopleValidator).validate(restaurant, booking);
    verify(bookingGateway, never()).save(booking);
  }

}