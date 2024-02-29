package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_CPF_NOT_FOUND;

import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.user.application.usecase.GetUserByCpfUseCase;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class GetUserByCpfInteractor implements GetUserByCpfUseCase {

  private final UserGateway userService;

  public GetUserByCpfInteractor(UserGateway userService) {
    this.userService = userService;
  }

  public User execute(String cpf) {
    return userService.findByCpf(cpf).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "cpf",
            USER_CPF_NOT_FOUND.formatted(cpf))));
  }
}
