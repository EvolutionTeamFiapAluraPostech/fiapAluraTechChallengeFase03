package br.com.fiaprestaurant.restaurant.domain.messages;

public final class AddressMessages {

  public static final String ENTER_THE_STREET = "Enter the street";
  public static final String ENTER_THE_NUMBER = "Enter the number";
  public static final String NUMBER_LENGTH_MUST_BE_LESS_THAN_100_CHARACTERS = "Number length must be between 3 and 255 characters. It has %s.";
  public static final String ENTER_THE_NEIGHBORHOOD = "Enter the neighborhood.";
  public static final String ENTER_THE_CITY = "Enter the city.";
  public static final String ENTER_THE_STATE = "Enter the state.";
  public static final String STREET_LENGTH_MUST_BE_BETWEEN_3_AND_255_CHARACTERS = "Street length must be between 3 and 255 characters. It has %s.";
  public static final String NEIGHBORHOOD_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS = "Neighborhood length must be between 3 and 100 characters. It has %s.";
  public static final String CITY_LENGTH_MUST_BE_BETWEEN_3_AND_100_CHARACTERS = "City length must be between 3 and 100 characters. It has %s.";
  public static final String STATE_LENGTH_MUST_BE_2_CHARACTERS = "State length must be 2 characters. It has %s.";

  private AddressMessages() {
  }
}
