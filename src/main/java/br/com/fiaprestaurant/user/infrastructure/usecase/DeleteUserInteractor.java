package br.com.fiaprestaurant.user.infrastructure.usecase;

import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.infrastructure.validator.UuidValidatorImpl;
import br.com.fiaprestaurant.user.application.usecase.DeleteUserUseCase;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserInteractor implements DeleteUserUseCase {

  private final UserGateway userService;
  private final UuidValidator uuidValidator;

  public DeleteUserInteractor(
      UserGateway userService,
      UuidValidatorImpl uuidValidator
  ) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
  }

  @Transactional
  public void execute(String userUuid) {
    uuidValidator.validate(userUuid);
    var user = userService.findUserByIdRequired(UUID.fromString(userUuid));
    userService.delete(user.getId());
  }
}
