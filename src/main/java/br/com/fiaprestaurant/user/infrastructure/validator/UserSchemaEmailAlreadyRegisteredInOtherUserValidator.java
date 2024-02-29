package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.infrastructure.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserSchemaEmailAlreadyRegisteredInOtherUserValidator implements
    UserEmailAlreadyRegisteredInOtherUserValidator {

  private final UserGateway userService;

  public UserSchemaEmailAlreadyRegisteredInOtherUserValidator(UserGateway userService) {
    this.userService = userService;
  }

  public void validate(String userUuid, String email) {
    var user = userService.findByEmail(email);
    if (user.isPresent() && emailAlreadyExistsInOtherUser(userUuid, user.get())) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "email",
          USER_EMAIL_ALREADY_EXISTS.formatted(email)));
    }
  }

  private static boolean emailAlreadyExistsInOtherUser(String userUuid, User user) {
    return !user.getId().equals(UUID.fromString(userUuid));
  }
}
