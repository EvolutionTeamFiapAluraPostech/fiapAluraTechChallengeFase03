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
    var numberCharInPassword = "(.*\\d.*)";
    if (!password.matches(numberCharInPassword)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR));
    }
  }

  private void validateLowerCharacterInPassword(String password) {
    var lowerCharInPassword = "(.*[a-z].*)";
    if (!password.matches(lowerCharInPassword)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR));
    }
  }

  private void validateUpperCharacterInPassword(String password) {
    var upperCharInPassword = "(.*[A-Z].*)";
    if (!password.matches(upperCharInPassword)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR));
    }
  }

  private void validateSpecialCharacterInPassword(String password) {
    var specialCharInPassword = "(.*[@#$%^&+=].*)";
    if (!password.matches(specialCharInPassword)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), PASSWORD_FIELD_NAME,
              USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR));
    }
  }
}
