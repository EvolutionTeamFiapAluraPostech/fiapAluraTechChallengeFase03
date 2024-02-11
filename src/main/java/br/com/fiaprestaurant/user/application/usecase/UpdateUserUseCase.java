package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.shared.domain.entity.validator.UuidValidator;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredInOtherUserValidator;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.domain.service.UserService;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserUseCase {

  private final UserService userService;
  private final UuidValidator uuidValidator;
  private final UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator;
  private final UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator;

  public UpdateUserUseCase(
      UserService userService,
      UuidValidator uuidValidator,
      UserEmailAlreadyRegisteredInOtherUserValidator userEmailAlreadyRegisteredInOtherUserValidator,
      UserCpfAlreadyRegisteredInOtherUserValidator userCpfAlreadyRegisteredInOtherUserValidator) {
    this.userService = userService;
    this.uuidValidator = uuidValidator;
    this.userEmailAlreadyRegisteredInOtherUserValidator = userEmailAlreadyRegisteredInOtherUserValidator;
    this.userCpfAlreadyRegisteredInOtherUserValidator = userCpfAlreadyRegisteredInOtherUserValidator;
  }

  @Transactional
  public UserSchema execute(String userUuid, UserSchema userSchemaWithUpdatedAttributes) {
    uuidValidator.validate(userUuid);
    userEmailAlreadyRegisteredInOtherUserValidator.validate(userUuid,
        userSchemaWithUpdatedAttributes.getEmail());
    userCpfAlreadyRegisteredInOtherUserValidator.validate(userUuid,
        userSchemaWithUpdatedAttributes.getCpf());

    var userSaved = userService.findUserByIdRequired(UUID.fromString(userUuid));
    var userToUpdate = updateAttibutesToUser(userSaved, userSchemaWithUpdatedAttributes);
    return userService.save(userToUpdate);
  }

  private UserSchema updateAttibutesToUser(UserSchema userSchemaSaved, UserSchema userSchemaToSave) {
    userSchemaSaved.setName(userSchemaToSave.getName());
    userSchemaSaved.setEmail(userSchemaToSave.getEmail());
    userSchemaSaved.setCpf(userSchemaToSave.getCpf());
    return userSchemaSaved;
  }

}
