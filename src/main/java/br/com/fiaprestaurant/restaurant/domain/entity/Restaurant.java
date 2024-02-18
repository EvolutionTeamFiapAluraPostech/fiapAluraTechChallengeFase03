package br.com.fiaprestaurant.restaurant.domain.entity;

import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantFields;
import br.com.fiaprestaurant.restaurant.domain.messages.RestaurantMessages;
import br.com.fiaprestaurant.restaurant.domain.valueobject.Address;
import br.com.fiaprestaurant.restaurant.domain.valueobject.Cnpj;
import br.com.fiaprestaurant.restaurant.domain.valueobject.TypeOfCuisine;
import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.UUID;
import org.springframework.validation.FieldError;

public class Restaurant {

  private UUID id;
  private final String name;
  private final Cnpj cnpj;
  private final TypeOfCuisine typeOfCuisine;
  private final Address address;
  private final String openAt;
  private final String closeAt;
  private final int peopleCapacity;

  public Restaurant(String name, String cnpj, String typeOfCuisine, Double latitude,
      Double longitude, String street, String number, String neighborhood, String city,
      String state, String postalCode, String openAt, String closeAt, int peopleCapacity) {
    validateNameIsNullOrEmpty(name);
    validateNameLength(name);
    validateOpenAtIsNullOrEmpty(openAt);
    validateOpenAtIsAValidNumber(openAt);
    validateCloseAtIsNullOrEmpty(closeAt);
    validateCloseAtIsAValidNumber(closeAt);
    validatePeopleCapacity(peopleCapacity);
    this.name = name;
    this.cnpj = new Cnpj(cnpj);
    this.typeOfCuisine = new TypeOfCuisine(typeOfCuisine);
    this.address = new Address(latitude, longitude, street, number, neighborhood, city, state,
        postalCode);
    this.openAt = openAt;
    this.closeAt = closeAt;
    this.peopleCapacity = peopleCapacity;
  }

  public Restaurant(UUID id, String name, String cnpj, String typeOfCuisine, Double latitude,
      Double longitude, String street, String number, String neighborhood, String city,
      String state, String postalCode, String openAt, String closeAt, int peopleCapacity) {
    this(name, cnpj, typeOfCuisine, latitude, longitude, street, number, neighborhood, city, state,
        postalCode, openAt, closeAt, peopleCapacity);
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public Cnpj getCnpj() {
    return cnpj;
  }

  public String getName() {
    return name;
  }

  public TypeOfCuisine getTypeOfCuisine() {
    return typeOfCuisine;
  }

  public Address getAddress() {
    return address;
  }

  public String getOpenAt() {
    return openAt;
  }

  public String getCloseAt() {
    return closeAt;
  }

  public int getPeopleCapacity() {
    return peopleCapacity;
  }

  private void validatePeopleCapacity(Integer peopleCapacity) {
    if (peopleCapacity == null || peopleCapacity <= 0) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_PEOPLE_CAPACITY_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_PEOPLE_CAPACITY));
    }
  }

  private void validateCloseAtIsAValidNumber(String closeAt) {
    if (closeAt.trim().length() != 5) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
    if (!closeAt.trim().contains(":")) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
    try {
      var hour = closeAt.split(":")[0];
      Integer.parseInt(hour);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
    try {
      var minute = closeAt.split(":")[1];
      Integer.parseInt(minute);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR));
    }
  }


  private void validateCloseAtIsNullOrEmpty(String closeAt) {
    if (closeAt == null || closeAt.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_CLOSE_AT_FIELD, RestaurantMessages.ENTER_CLOSING_TIME));
    }
  }

  private void validateOpenAtIsAValidNumber(String openAt) {
    if (openAt.trim().length() != 5) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
    if (!openAt.trim().contains(":")) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
    try {
      var hour = openAt.split(":")[0];
      Integer.parseInt(hour);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
    try {
      var minute = openAt.split(":")[1];
      Integer.parseInt(minute);
    } catch (NumberFormatException e) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR));
    }
  }

  private void validateOpenAtIsNullOrEmpty(String openAt) {
    if (openAt == null || openAt.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_OPEN_AT_FIELD,
          RestaurantMessages.ENTER_RESTAURANT_OPENING_HOURS));
    }
  }

  private void validateNameIsNullOrEmpty(String name) {
    if (name == null || name.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_NAME_FIELD, RestaurantMessages.ENTER_THE_RESTAURANT_NAME));
    }
  }

  private void validateNameLength(String name) {
    if (name.trim().length() < 3 || name.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          RestaurantFields.RESTAURANT_NAME_FIELD,
          RestaurantMessages.RESTAURANT_NAME_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS.formatted(
              name.trim().length())));
    }
  }

}
