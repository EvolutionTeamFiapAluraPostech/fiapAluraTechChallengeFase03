package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.user.domain.entity.User;

public interface GetUserByCpfUseCase {

  User execute(String cpf);
}
