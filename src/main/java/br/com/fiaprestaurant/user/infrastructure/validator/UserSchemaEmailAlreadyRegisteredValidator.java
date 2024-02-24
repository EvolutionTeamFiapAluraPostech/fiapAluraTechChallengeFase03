package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserSchemaEmailAlreadyRegisteredValidator implements
    UserEmailAlreadyRegisteredValidator {

  private final UserGateway userService;

  public UserSchemaEmailAlreadyRegisteredValidator(UserGateway userService) {
    this.userService = userService;
  }

  public void validate(String email) {
    var user = userService.findByEmail(email);
    if (user.isPresent()) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "email",
          USER_EMAIL_ALREADY_EXISTS.formatted(email)));
    }
  }
}
