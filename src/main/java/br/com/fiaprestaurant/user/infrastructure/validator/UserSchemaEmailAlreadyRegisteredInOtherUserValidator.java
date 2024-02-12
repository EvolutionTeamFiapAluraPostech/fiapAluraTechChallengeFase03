package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.domain.service.UserService;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserSchemaEmailAlreadyRegisteredInOtherUserValidator implements
    UserEmailAlreadyRegisteredInOtherUserValidator {

  private final UserService userService;

  public UserSchemaEmailAlreadyRegisteredInOtherUserValidator(UserService userService) {
    this.userService = userService;
  }

  public void validate(String userUuid, String email) {
    var user = userService.findByEmail(email);
    if (user.isPresent() && emailAlreadyExistsInOtherUser(userUuid, user.get())) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "email",
          USER_EMAIL_ALREADY_EXISTS.formatted(email)));
    }
  }

  private static boolean emailAlreadyExistsInOtherUser(String userUuid, UserSchema userSchema) {
    return !userSchema.getId().equals(UUID.fromString(userUuid));
  }
}
