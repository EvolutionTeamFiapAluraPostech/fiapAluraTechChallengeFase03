package br.com.fiaprestaurant.user.application.usecase;

import br.com.fiaprestaurant.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllUsersUseCase {
  Page<User> execute(Pageable pageable);
}
