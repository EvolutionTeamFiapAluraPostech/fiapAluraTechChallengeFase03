package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.domain.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

  private final UserService userService;
  private final UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  private final PasswordEncoder passwordEncoder;
  private final UserCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator;

  public CreateUserUseCase(
      UserService userService,
      UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator,
      PasswordEncoder passwordEncoder,
      UserCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator) {
    this.userService = userService;
    this.userEmailAlreadyRegisteredValidator = userEmailAlreadyRegisteredValidator;
    this.passwordEncoder = passwordEncoder;
    this.userCpfAlreadyRegisteredValidator = userCpfAlreadyRegisteredValidator;
  }

  @Transactional
  public User execute(User user) {
    userEmailAlreadyRegisteredValidator.validate(user.getEmail().address());
    userCpfAlreadyRegisteredValidator.validate(user.getCpf().cpf());
    var passwordEncoded = passwordEncoder.encode(user.getPassword().getPasswordValue());
    var userSchema = UserSchema.builder().name(user.getName()).email(user.getEmail().address())
        .cpf(user.getCpf().cpf()).password(passwordEncoded).build();
    var userSchemaSaved = userService.save(userSchema);
    return new User(userSchemaSaved.getId(), userSchemaSaved.getName(), userSchemaSaved.getEmail(),
        userSchemaSaved.getCpf(), userSchemaSaved.getPassword());
  }
}
