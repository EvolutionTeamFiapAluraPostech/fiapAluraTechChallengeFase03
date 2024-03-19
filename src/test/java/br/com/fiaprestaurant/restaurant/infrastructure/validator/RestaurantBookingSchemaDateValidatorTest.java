package br.com.fiaprestaurant.restaurant.infrastructure.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantBookingSchemaDateValidatorTest {

  @InjectMocks
  private RestaurantBookingSchemaDateValidator restaurantBookingSchemaDateValidator;

  @Test
  void shouldValidateRestaurantBookingSchemaDate() {
    var bookingDate = LocalDateTime.now().plusDays(1);
    assertThatCode(() -> restaurantBookingSchemaDateValidator.validate(
        bookingDate)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowExceptionWhenValidateRestaurantBookingSchemaDate() {
    var bookingDate = LocalDateTime.now().minusMinutes(1);
    assertThatThrownBy(() -> restaurantBookingSchemaDateValidator.validate(
        bookingDate)).isInstanceOf(ValidatorException.class);
  }
}
