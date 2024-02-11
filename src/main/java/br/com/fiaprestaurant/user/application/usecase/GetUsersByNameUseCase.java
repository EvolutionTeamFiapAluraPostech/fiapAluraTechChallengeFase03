package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.domain.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByNameUseCase {

  private final UserService userService;

  public GetUsersByNameUseCase(UserService userService) {
    this.userService = userService;
  }

  public Page<UserSchema> execute(String name, Pageable pageable) {
    return userService.findByNamePageable(name, pageable);
  }
}
