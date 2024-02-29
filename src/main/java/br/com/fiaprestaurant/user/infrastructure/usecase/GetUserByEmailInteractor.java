package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_NOT_FOUND;

import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.shared.infrastructure.validator.EmailValidator;
import br.com.fiaprestaurant.user.application.usecase.GetUserByEmailUseCase;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class GetUserByEmailInteractor implements GetUserByEmailUseCase {

  private final UserGateway userService;
  private final EmailValidator emailValidator;

  public GetUserByEmailInteractor(
      UserGateway userService,
      EmailValidator emailValidator) {
    this.userService = userService;
    this.emailValidator = emailValidator;
  }

  public User execute(String email) {
    emailValidator.validate(email);
    return userService.findByEmail(email).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "User",
            USER_EMAIL_NOT_FOUND.formatted(email))));
  }
}
