package br.com.fiaprestaurant.user.infrastructure.usecase;

import br.com.fiaprestaurant.user.application.usecase.CreateUserUseCase;
import br.com.fiaprestaurant.user.application.validator.UserCpfAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.application.validator.UserEmailAlreadyRegisteredValidator;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserInteractor implements CreateUserUseCase {

  private final UserGateway userService;
  private final UserEmailAlreadyRegisteredValidator userEmailAlreadyRegisteredValidator;
  private final PasswordEncoder passwordEncoder;
  private final UserCpfAlreadyRegisteredValidator userCpfAlreadyRegisteredValidator;

  public CreateUserInteractor(
      UserGateway userService,
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
    userCpfAlreadyRegisteredValidator.validate(user.getCpf().getCpfNumber());
    var passwordEncoded = passwordEncoder.encode(user.getPassword().passwordValue());
    var userToSave = new User(user.getName(), user.getEmail().address(),
        user.getCpf().getCpfNumber(), passwordEncoded);
    return userService.save(userToSave);
  }
}
