package br.com.fiaprestaurant.user.domain.repository;

import br.com.fiaprestaurant.user.infrastructure.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

  Optional<User> findByEmail(String email);

  Optional<User> findByCpf(String cpf);

  Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);

}
