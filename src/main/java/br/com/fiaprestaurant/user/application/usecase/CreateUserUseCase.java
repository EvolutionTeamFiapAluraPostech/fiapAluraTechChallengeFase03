package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.user.domain.entity.User;

public interface CreateUserUseCase {

  User execute(User user);
}
