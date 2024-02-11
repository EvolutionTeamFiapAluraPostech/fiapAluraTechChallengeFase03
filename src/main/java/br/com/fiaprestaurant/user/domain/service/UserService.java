package br.com.fiaprestaurant.user.domain.service;

import br.com.fiaprestaurant.user.infrastructure.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User save(User user);

  Page<User> getAllUsersPaginated(Pageable pageable);

  Optional<User> findByEmail(String email);

  User findByEmailRequired(String email);

  Optional<User> findByCpf(String cpf);

  Page<User> findByNamePageable(String name, Pageable pageable);

  Optional<User> findById(UUID uuid);

  User findUserByIdRequired(UUID userUuid);
}
