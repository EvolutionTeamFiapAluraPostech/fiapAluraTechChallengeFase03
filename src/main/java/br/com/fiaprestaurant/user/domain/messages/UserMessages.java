package br.com.fiaprestaurant.user.domain.messages;

public final class UserMessages {

  private UserMessages(){
  }

  public static final String USER_ID_NOT_FOUND = "User not found by ID %s.";
  public static final String USER_ID_REQUIRED = "User ID is required.";
  public static final String USER_NAME_REQUIRED = "User name is required.";
  public static final String USER_NAME_MIN_LENGTH = "Min name length is 2 characters.";
  public static final String USER_NAME_MAX_LENGTH = "Max name length is 500 characters.";
  public static final String USER_EMAIL_NOT_FOUND = "User not found by email %s.";
  public static final String USER_EMAIL_ALREADY_EXISTS = "User already exists with email %s";
  public static final String USER_CPF_ALREADY_EXISTS = "User already exists with cpf %s";
  public static final String USER_CPF_NOT_FOUND = "User not found by cpf %s";
  public static final String USER_EMAIL_INVALID = "User email is invalid %s.";
  public static final String USER_PASSWORD_CANNOT_BE_NULL_OR_EMPTY = "Password cannot be null or empty.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR = "Password must have at least one number character.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR = "Password must have at least one lower character.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR = "Password must have at least one upper character.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR = "Password must have at least one special character @#$%^&+= .";
}
