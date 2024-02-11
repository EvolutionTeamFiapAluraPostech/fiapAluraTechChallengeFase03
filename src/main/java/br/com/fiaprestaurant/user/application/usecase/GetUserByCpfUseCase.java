package br.com.fiaprestaurant.user.application.usecase;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_CPF_NOT_FOUND;

import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.domain.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class GetUserByCpfUseCase {

  private final UserService userService;

  public GetUserByCpfUseCase(UserService userService) {
    this.userService = userService;
  }

  public UserSchema execute(String cpf) {
    return userService.findByCpf(cpf).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "cpf",
            USER_CPF_NOT_FOUND.formatted(cpf))));
  }
}
