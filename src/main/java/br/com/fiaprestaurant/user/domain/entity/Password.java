package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CHAR;

import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record Password(String passwordValue) {

  private static final String PASSWORD_FIELD_NAME = "password";
  public static final String NUMBER_IN_PASSWORD_REGEX = "(.*\\d.*)";
  public static final String LOWER_CHAR_IN_PASSWORD_REGEX = "(.*[a-z].*)";
  public static final String UPPER_CHAR_IN_PASSWORD_REGEX = "(.*[A-Z].*)";
  public static final String SPECIAL_CHAR_IN_PASSWORD_REGEX = "(.*[@#$%^&+=].*)";

  public Password {
    if (passwordValue != null && !passwordValue.trim().isEmpty()) {
      this.validateNumberInPassword(passwordValue);
      this.validateLowerCharacterInPassword(passwordValue);
      this.validateUpperCharacterInPassword(passwordValue);
      this.validateSpecialCharacterInPassword(passwordValue);
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
