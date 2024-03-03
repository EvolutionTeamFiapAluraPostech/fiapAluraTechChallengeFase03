package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields.RESTAURANT_PEOPLE_CAPACITY_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages.ENTER_RESTAURANT_PEOPLE_CAPACITY;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record PeopleCapacity(Integer peopleCapacityValue) {

  public PeopleCapacity {
    validatePeopleCapacity(peopleCapacityValue);
  }

  private void validatePeopleCapacity(Integer peopleCapacity) {
    if (peopleCapacity == null || peopleCapacity <= 0) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RESTAURANT_PEOPLE_CAPACITY_FIELD, ENTER_RESTAURANT_PEOPLE_CAPACITY));
    }
  }
}
