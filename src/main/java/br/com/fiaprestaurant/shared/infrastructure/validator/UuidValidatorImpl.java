package br.com.fiaprestaurant.shared.infrastructure.validator;

import static br.com.fiaprestaurant.shared.domain.messages.SharedMessages.UUID_INVALID;

import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.domain.exception.ValidatorException;
import br.com.fiaprestaurant.shared.util.IsUUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UuidValidatorImpl implements UuidValidator {

  public void validate(String uuid) {
    if (!IsUUID.isUUID().matches(uuid)) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), "UUID",
          UUID_INVALID.formatted(uuid)));
    }
  }
}
