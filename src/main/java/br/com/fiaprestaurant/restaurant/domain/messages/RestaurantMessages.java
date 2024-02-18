package br.com.fiaprestaurant.restaurant.domain.messages;

public final class RestaurantMessages {

  public static final String RESTAURANT_NOT_FOUND_WITH_ID = "Restaurant not found with ID: %s.";
  public static final String RESTAURANT_NOT_FOUND_WITH_CNPJ = "Restaurant not found with CNPJ: %s.";
  public static final String RESTAURANT_ALREADY_EXISTS_WITH_CNPJ = "Restaurant already exists with CNPJ %s.";
  public static final String ENTER_THE_RESTAURANT_NAME = "Enter the restaurant name.";
  public static final String RESTAURANT_NAME_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS = "Restaurant name must have between 3 and 100 characters. It has %s.";
  public static final String ENTER_RESTAURANT_OPENING_HOURS = "Enter opening hours.";
  public static final String ENTER_CLOSING_TIME = "Enter closing time.";
  public static final String ENTER_RESTAURANT_PEOPLE_CAPACITY = "Enter people capacity.";
  public static final String ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR = "Enter a valid number for opening hour.";
  public static final String ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR = "Enter a valid number for closing hour.";

  private RestaurantMessages() {
  }
}
