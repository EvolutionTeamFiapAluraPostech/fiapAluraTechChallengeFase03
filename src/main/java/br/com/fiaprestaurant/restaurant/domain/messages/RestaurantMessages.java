package br.com.fiaprestaurant.restaurant.domain.messages;

public final class RestaurantMessages {

  public static final String RESTAURANT_NOT_FOUND = "Restaurant not found.";
  public static final String RESTAURANT_NOT_FOUND_WITH_ID = "Restaurant not found with ID: %s.";
  public static final String RESTAURANT_ALREADY_EXISTS_WITH_CNPJ = "Restaurant already exists with CNPJ %s.";
  public static final String RESTAURANT_ALREADY_EXISTS_WITH_CNPJ_BUT_OTHER_ID = "Restaurant already exists with CNPJ %s, but with other ID.";
  public static final String RESTAURANT_NAME_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS = "Restaurant name must have between 3 and 100 characters. It has %s.";
  public static final String RESTAURANT_REVIEW_DESCRIPTION_LENGTH_MUST_HAVE_BETWEEN_3_AND_100_CHARACTERS = "Restaurant review description must have between 3 and 100 characters. It has %s.";
  public static final String RESTAURANT_REVIEW_SCORE_MUST_BE_GREATER_THAN_ZERO = "Restaurant review score must be greater than zero.";
  public static final String RESTAURANT_REVIEW_SCORE_MUST_BE_GREATER_THAN_ZERO_AND_LESS_THAN_TEN = "Restaurant review score must be greater than zero and less than ten.";
  public static final String RESTAURANT_BOOKING_DATE_MUST_BE_GREATER_THAN_ACTUAL_DATE = "Restaurant booking date must be greater than actual date. Actual date: %s. Booking date: %s.";
  public static final String RESTAURANT_BOOKING_PEOPLE_CAPACITY_MUST_BE_LOWER_THAN_MAX_RESTAURANT_PEOPLE_CAPACITY = "Restaurant booking people capacity must be lower than restaurant people capacity.";
  public static final String RESTAURANT_BOOKING_NOT_FOUND_WITH_ID = "Restaurant booking not found with ID: %s.";
  public static final String RESTAURANT_BOOKING_ALREADY_CLOSED = "Restaurant booking is already closed.";
  public static final String RESTAURANT_BOOKING_ALREADY_CANCELED = "Restaurant booking is already canceled.";
  public static final String ENTER_THE_RESTAURANT_ID = "Enter the restaurant ID.";
  public static final String ENTER_THE_RESTAURANT_NAME = "Enter the restaurant name.";
  public static final String ENTER_RESTAURANT_OPENING_HOURS = "Enter opening hours.";
  public static final String ENTER_CLOSING_TIME = "Enter closing time.";
  public static final String ENTER_RESTAURANT_PEOPLE_CAPACITY = "Enter people capacity.";
  public static final String ENTER_RESTAURANT_VALID_NUMBER_FOR_OPENING_HOUR = "Enter a valid number for opening hour.";
  public static final String ENTER_RESTAURANT_VALID_NUMBER_FOR_CLOSING_HOUR = "Enter a valid number for closing hour.";
  public static final String ENTER_RESTAURANT_REVIEW_DESCRIPTION = "Enter a review description.";
  public static final String ENTER_RESTAURANT_BOOKING_DATE = "Enter the restaurant booking date.";
  public static final String ENTER_RESTAURANT_BOOKING_STATE = "Enter the restaurant booking state.";
  public static final String ENTER_RESTAURANT_START_BOOKING_DATE = "Enter the restaurant start booking date.";
  public static final String ENTER_RESTAURANT_END_BOOKING_DATE = "Enter the restaurant end booking date.";

  private RestaurantMessages() {
  }
}
