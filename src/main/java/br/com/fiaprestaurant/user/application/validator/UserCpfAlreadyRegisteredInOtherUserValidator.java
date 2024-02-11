package br.com.fiaprestaurant.user.application.validator;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_CPF_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.domain.service.UserService;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.infrastructure.service.UserSchemaService;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserCpfAlreadyRegisteredInOtherUserValidator {

  private final UserService userService;

  public UserCpfAlreadyRegisteredInOtherUserValidator(UserSchemaService userService) {
    this.userService = userService;
  }

  public void validate(String userUuid, String cpf) {
    var user = userService.findByCpf(cpf);
    if (user.isPresent() && cpfAlreadyExistsInOtherUser(userUuid, user.get())) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "cpf",
          USER_CPF_ALREADY_EXISTS.formatted(cpf)));
    }
  }

  private static boolean cpfAlreadyExistsInOtherUser(String userUuid, UserSchema userSchema) {
    return !userSchema.getId().equals(UUID.fromString(userUuid));
  }
}
