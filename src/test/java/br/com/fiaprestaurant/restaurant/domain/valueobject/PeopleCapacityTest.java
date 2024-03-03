package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_PEOPLE_CAPACITY;
import static org.assertj.core.api.Assertions.assertThatCode;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PeopleCapacityTest {

  @Test
  void shouldCreatePeopleCapacity() {
    assertThatCode(() -> new PeopleCapacity(100)).doesNotThrowAnyException();
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(ints = {-1, 0})
  void shouldThrowExceptionWhenPeopleCapacityNumberIsInvalid(Integer peopleCapacity) {
    Assertions.assertThatThrownBy(() -> new PeopleCapacity(peopleCapacity))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(ENTER_RESTAURANT_PEOPLE_CAPACITY);
  }


}