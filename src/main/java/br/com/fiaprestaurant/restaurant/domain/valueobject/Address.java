package br.com.fiaprestaurant.restaurant.domain.valueobject;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Address {

  public static final String ENTER_THE_STREET = "Enter the street";
  public static final String STREET_FIELD = "street";
  public static final String ENTER_THE_NUMBER = "Enter the number";
  public static final String NUMBER_FIELD = "number";
  public static final String NUMBER_LENGTH_MUST_BE_LESS_THAN_100_CHARACTERS = "Number length must be between 3 and 255 characters. It has %s.";
  public static final String NEIGHBORHOOD_FIELD = "neighborhood";
  public static final String ENTER_THE_NEIGHBORHOOD = "Enter the neighborhood.";
  public static final String CITY_FIELD = "city";
  public static final String ENTER_THE_CITY = "Enter the city.";
  public static final String STATE_FIELD = "state";
  public static final String ENTER_THE_STATE = "Enter the state.";
  public static final String STREET_LENGTH_MUST_BE_BETWEEN_3_AND_255_CHARACTERS = "Street length must be between 3 and 255 characters. It has %s.";
  public static final String NEIGHBORHOOD_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS = "Neighborhood length must be between 3 and 100 characters. It has %s.";
  public static final String CITY_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS = "City length must be between 3 and 100 characters. It has %s.";
  public static final String STATE_LENGTH_MUST_BE_2_CHARACTERS = "State length must be 2 characters. It has %s.";
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
    this.street = street;
    this.number = number;
    this.neighborhood = neighborhood;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
  }

  private void validateLengthNumber(String number) {
    if (number.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          NUMBER_LENGTH_MUST_BE_LESS_THAN_100_CHARACTERS.formatted(number.trim().length())));
    }
  }

  private void validateNullOrEmptyNumber(String number) {
    if (number == null || number.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          ENTER_THE_NUMBER));
    }
  }

  private void validateLengthState(String state) {
    if (state.trim().length() != 2) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STATE_FIELD,
          STATE_LENGTH_MUST_BE_2_CHARACTERS.formatted(state.trim().length())));
    }
  }

  private void validateNullOrEmptyState(String state) {
    if (state == null || state.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STATE_FIELD,
          ENTER_THE_STATE));
    }
  }

  private void validateLengthCity(String city) {
    if (city.trim().length() < 3 || city.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CITY_FIELD,
          CITY_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS.formatted(city.trim().length())));
    }
  }

  private void validateNullOrEmptyCity(String city) {
    if (city == null || city.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CITY_FIELD,
          ENTER_THE_CITY));
    }
  }

  private void validateLengthNeighborhood(String neighborhood) {
    if (neighborhood.trim().length() < 3 || neighborhood.trim().length() > 100) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          NEIGHBORHOOD_FIELD, NEIGHBORHOOD_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS.formatted(
          neighborhood.trim().length())));
    }
  }

  private void validateNullOrEmptyNeighborhood(String neighborhood) {
    if (neighborhood == null || neighborhood.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          NEIGHBORHOOD_FIELD, ENTER_THE_NEIGHBORHOOD));
    }
  }

  private void validateLengthStreet(String street) {
    if (street.trim().length() < 3 || street.trim().length() > 255) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STREET_FIELD,
          STREET_LENGTH_MUST_BE_BETWEEN_3_AND_255_CHARACTERS.formatted(street.trim().length())));
    }
  }

  private void validateNullOrEmptyStreet(String street) {
    if (street == null || street.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STREET_FIELD,
          ENTER_THE_STREET));
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
