package br.com.fiaprestaurant.restaurant.domain.valueobject;

import static br.com.fiaprestaurant.restaurant.domain.messages.AddressFields.CITY_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.AddressFields.NEIGHBORHOOD_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.AddressFields.NUMBER_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.AddressFields.STATE_FIELD;
import static br.com.fiaprestaurant.restaurant.domain.messages.AddressFields.STREET_FIELD;

import br.com.fiaprestaurant.restaurant.domain.messages.AddressMessages;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Address {

  private final Coordinates coordinates;
  private final String street;
  private final String number;
  private final String neighborhood;
  private final String city;
  private final String state;
  private final String postalCode;

  public Address(Double latitude, Double longitude, String street, String number,
      String neighborhood, String city, String state, String postalCode) {
    validateNullOrEmptyStreet(street);
    validateLengthStreet(street);
    validateNullOrEmptyNumber(number);
    validateLengthNumber(number);
    validateNullOrEmptyNeighborhood(neighborhood);
    validateLengthNeighborhood(neighborhood);
    validateNullOrEmptyCity(city);
    validateLengthCity(city);
    validateNullOrEmptyState(state);
    validateLengthState(state);
    this.coordinates = new Coordinates(latitude, longitude);
    this.street = street.trim();
    this.number = number.trim();
    this.neighborhood = neighborhood.trim();
    this.city = city.trim();
    this.state = state.trim();
    this.postalCode = postalCode;
  }

  private void validateLengthNumber(String number) {
    if (number.trim().length() < 3 || number.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          AddressMessages.NUMBER_LENGTH_MUST_BE_LESS_THAN_100_CHARACTERS.formatted(number.trim().length())));
    }
  }

  private void validateNullOrEmptyNumber(String number) {
    if (number == null || number.isBlank()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          AddressMessages.ENTER_THE_NUMBER));
    }
  }

  private void validateLengthState(String state) {
    if (state.trim().length() != 2) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STATE_FIELD,
          AddressMessages.STATE_LENGTH_MUST_BE_2_CHARACTERS.formatted(state.trim().length())));
    }
  }

  private void validateNullOrEmptyState(String state) {
    if (state == null || state.isBlank()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STATE_FIELD,
          AddressMessages.ENTER_THE_STATE));
    }
  }

  private void validateLengthCity(String city) {
    if (city.trim().length() < 3 || city.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CITY_FIELD,
          AddressMessages.CITY_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS.formatted(city.trim().length())));
    }
  }

  private void validateNullOrEmptyCity(String city) {
    if (city == null || city.isBlank()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CITY_FIELD,
          AddressMessages.ENTER_THE_CITY));
    }
  }

  private void validateLengthNeighborhood(String neighborhood) {
    if (neighborhood.trim().length() < 3 || neighborhood.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          NEIGHBORHOOD_FIELD, AddressMessages.NEIGHBORHOOD_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS.formatted(
          neighborhood.trim().length())));
    }
  }

  private void validateNullOrEmptyNeighborhood(String neighborhood) {
    if (neighborhood == null || neighborhood.isBlank()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          NEIGHBORHOOD_FIELD, AddressMessages.ENTER_THE_NEIGHBORHOOD));
    }
  }

  private void validateLengthStreet(String street) {
    if (street.trim().length() < 3 || street.trim().length() > 255) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STREET_FIELD,
          AddressMessages.STREET_LENGTH_MUST_BE_BETWEEN_3_AND_255_CHARACTERS.formatted(street.trim().length())));
    }
  }

  private void validateNullOrEmptyStreet(String street) {
    if (street == null || street.isBlank()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STREET_FIELD,
          AddressMessages.ENTER_THE_STREET));
    }
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public String getStreet() {
    return street;
  }

  public String getNumber() {
    return number;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }
}
