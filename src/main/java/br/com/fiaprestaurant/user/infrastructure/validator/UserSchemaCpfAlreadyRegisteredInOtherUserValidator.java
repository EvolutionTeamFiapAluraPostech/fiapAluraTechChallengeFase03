package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_CPF_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.infrastructure.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import br.com.fiaprestaurant.user.infrastructure.gateway.UserSchemaGateway;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserSchemaCpfAlreadyRegisteredInOtherUserValidator implements
    UserCpfAlreadyRegisteredInOtherUserValidator {

  private final UserGateway userService;

  public UserSchemaCpfAlreadyRegisteredInOtherUserValidator(UserSchemaGateway userService) {
    this.userService = userService;
  }

  public void validate(String userUuid, String cpf) {
    var user = userService.findByCpf(cpf);
    if (user.isPresent() && cpfAlreadyExistsInOtherUser(userUuid, user.get())) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "cpf",
          USER_CPF_ALREADY_EXISTS.formatted(cpf)));
    }
  }

  private static boolean cpfAlreadyExistsInOtherUser(String userUuid, User user) {
    return !user.getId().equals(UUID.fromString(userUuid));
  }
}
