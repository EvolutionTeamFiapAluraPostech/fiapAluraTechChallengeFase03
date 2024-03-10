package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class BookingStateTest {

  @Test
  void shouldCreateBookingState() {
    var bookingState = new BookingState(BookingStateEnum.RESERVED.getLabel());
    assertThat(bookingState).isNotNull();
    assertThat(bookingState.getValue()).isNotBlank()
        .isEqualTo(BookingStateEnum.RESERVED.getLabel());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowExceptionWhenBookingStateIsNullOrEmpty(String value) {
    assertThatThrownBy(() -> new BookingState(value))
        .isInstanceOf(ValidatorException.class);
  }

}