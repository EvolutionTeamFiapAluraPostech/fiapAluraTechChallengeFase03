package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_CANNOT_BE_NULL_OR_EMPTY;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Password {

  private static final String PASSWORD_FIELD_NAME = "password";
  public static final String NUMBER_IN_PASSWORD_REGEX = "(.*\\d.*)";
  public static final String LOWER_CHAR_IN_PASSWORD_REGEX = "(.*[a-z].*)";
  public static final String UPPER_CHAR_IN_PASSWORD_REGEX = "(.*[A-Z].*)";
  public static final String SPECIAL_CHAR_IN_PASSWORD_REGEX = "(.*[@#$%^&+=].*)";
  private final String passwordValue;

  public Password(String passwordValue) {
    this.validateNullOrEmptyPassword(passwordValue);
    this.validateNumberInPassword(passwordValue);
    this.validateLowerCharacterInPassword(passwordValue);
    this.validateUpperCharacterInPassword(passwordValue);
    this.validateSpecialCharacterInPassword(passwordValue);
    this.passwordValue = passwordValue;
  }

  public String getPasswordValue() {
    return passwordValue;
  }

  private void validateNullOrEmptyPassword(String passwordValue) {
    if (passwordValue == null || passwordValue.isEmpty()) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_CANNOT_BE_NULL_OR_EMPTY));
    }
  }

  private void validateNumberInPassword(String password) {
    if (!password.matches(NUMBER_IN_PASSWORD_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR));
    }
  }

  private void validateLowerCharacterInPassword(String password) {
    if (!password.matches(LOWER_CHAR_IN_PASSWORD_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR));
    }
  }

  private void validateUpperCharacterInPassword(String password) {
    if (!password.matches(UPPER_CHAR_IN_PASSWORD_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR));
    }
  }

  private void validateSpecialCharacterInPassword(String password) {
    if (!password.matches(SPECIAL_CHAR_IN_PASSWORD_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR));
    }
  }
}
