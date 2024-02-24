package br.com.fiaprestaurant.user.infrastructure.usecase;

import br.com.fiaprestaurant.user.application.usecase.GetAllUsersUseCase;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllUsersInteractor implements GetAllUsersUseCase {

  private final UserGateway userService;

  public GetAllUsersInteractor(UserGateway userService) {
    this.userService = userService;
  }

  public Page<User> execute(Pageable pageable) {
    return userService.getAllUsersPaginated(pageable);
  }
}
