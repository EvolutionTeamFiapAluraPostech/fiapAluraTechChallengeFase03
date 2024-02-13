package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.shared.infrastructure.validator.UuidValidatorImpl;
import br.com.fiaprestaurant.user.domain.service.UserService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserUseCase {

  private final UserService userService;
  private final br.com.fiaprestaurant.shared.domain.validator.UuidValidator uuidValidator;

  public DeleteUserUseCase(
      UserService userService,
      UuidValidatorImpl uuidValidator
  ) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
  }

  @Transactional
  public void execute(String userUuid) {
    uuidValidator.validate(userUuid);
    var user = userService.findUserByIdRequired(UUID.fromString(userUuid));
    user.setDeleted(true);
    userService.save(user);
  }
}
