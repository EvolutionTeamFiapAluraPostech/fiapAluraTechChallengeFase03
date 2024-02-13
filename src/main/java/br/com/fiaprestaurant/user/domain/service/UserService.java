package br.com.fiaprestaurant.user.domain.service;

import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  User save(User user);

  User update(UUID id, User user);

  void delete(UUID id);

  Page<User> getAllUsersPaginated(Pageable pageable);

  Optional<User> findByEmail(String email);

  UserSchema findByEmailRequired(String email);

  Optional<User> findByCpf(String cpf);

  Page<User> findByNamePageable(String name, Pageable pageable);

  Optional<User> findById(UUID uuid);

  User findUserByIdRequired(UUID userUuid);
}
