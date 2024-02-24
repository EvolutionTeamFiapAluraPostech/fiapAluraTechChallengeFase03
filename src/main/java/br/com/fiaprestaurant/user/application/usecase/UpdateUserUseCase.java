package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.user.domain.entity.User;

public interface UpdateUserUseCase {

  User execute(String userUuid, User userWithUpdatedAttributes);

}
