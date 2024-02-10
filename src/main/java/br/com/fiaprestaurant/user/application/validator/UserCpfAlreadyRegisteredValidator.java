package br.com.fiaprestaurant.user.application.validator;

import static br.com.fiaprestaurant.user.model.messages.UserMessages.USER_CPF_ALREADY_EXISTS;

import br.com.fiaprestaurant.shared.exception.DuplicatedException;
import br.com.fiaprestaurant.user.model.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class UserCpfAlreadyRegisteredValidator {

  private final UserService userService;

  public UserCpfAlreadyRegisteredValidator(UserService userService) {
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
