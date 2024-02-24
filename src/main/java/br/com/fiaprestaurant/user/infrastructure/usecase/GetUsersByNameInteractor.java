package br.com.fiaprestaurant.user.infrastructure.usecase;

import br.com.fiaprestaurant.user.application.usecase.GetUsersByNameUseCase;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByNameInteractor implements GetUsersByNameUseCase {

  private final UserGateway userService;

  public GetUsersByNameInteractor(UserGateway userService) {
    this.userService = userService;
  }

  public Page<User> execute(String name, Pageable pageable) {
    return userService.findByNamePageable(name, pageable);
  }
}
