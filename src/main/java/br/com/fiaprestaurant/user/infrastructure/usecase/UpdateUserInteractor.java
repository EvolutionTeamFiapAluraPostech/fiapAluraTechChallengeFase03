package br.com.fiaprestaurant.user.infrastructure.usecase;

import br.com.fiaprestaurant.shared.domain.validator.UuidValidator;
import br.com.fiaprestaurant.shared.infrastructure.validator.UuidValidatorImpl;
import br.com.fiaprestaurant.user.application.usecase.UpdateUserUseCase;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserInteractor implements UpdateUserUseCase {

  private final UserGateway userService;
  private final UuidValidator uuidValidator;
  private final UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;
  private final UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator;

  public UpdateUserInteractor(
      UserGateway userService,
      UuidValidatorImpl uuidValidator,
      UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator,
      UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
    this.userEmailAlreadyRegisteredInOtherUserValidator = userEmailAlreadyRegisteredInOtherUserValidator;
    this.userCpfAlreadyRegisteredInOtherUserValidator = userCpfAlreadyRegisteredInOtherUserValidator;
  }

  @Transactional
  public User execute(String userUuid, User userWithUpdatedAttributes) {
    uuidValidator.validate(userUuid);
    userEmailAlreadyRegisteredInOtherUserValidator.validate(userUuid,
        userWithUpdatedAttributes.getEmail().address());
    userCpfAlreadyRegisteredInOtherUserValidator.validate(userUuid,
        userWithUpdatedAttributes.getCpf().getCpfNumber());
    var userFound = userService.findUserByIdRequired(UUID.fromString(userUuid));
    return userService.update(userFound.getId(), userWithUpdatedAttributes);
  }

}
