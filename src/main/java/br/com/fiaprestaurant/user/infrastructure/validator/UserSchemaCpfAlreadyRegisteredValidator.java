package br.com.fiaprestaurant.user.infrastructure.validator;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_CPF_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserSchemaCpfAlreadyRegisteredValidator implements UserCpfAlreadyRegisteredValidator {

  private final UserGateway userService;

  public UserSchemaCpfAlreadyRegisteredValidator(UserGateway userService) {
    this.userService = userService;
  }

  public void validate(String cpf) {
    var user = userService.findByCpf(cpf);
    if (user.isPresent()) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), "cpf",
          USER_CPF_ALREADY_EXISTS.formatted(cpf)));
    }
  }
}
