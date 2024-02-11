package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.user.domain.messages.EmailMessages.EMAIL_FIELD_NAME;
import static br.com.fiaprestaurant.user.domain.messages.EmailMessages.EMAIL_INVALID;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record Email(String address) {

  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  public Email {
    if (address == null || !address.matches(EMAIL_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EMAIL_FIELD_NAME, EMAIL_INVALID));
    }
  }
}
