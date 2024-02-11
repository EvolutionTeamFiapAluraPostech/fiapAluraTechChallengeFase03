package br.com.fiaprestaurant.user.domain.service;

import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  UserSchema save(UserSchema userSchema);

  Page<UserSchema> getAllUsersPaginated(Pageable pageable);

  Optional<UserSchema> findByEmail(String email);

  UserSchema findByEmailRequired(String email);

  Optional<UserSchema> findByCpf(String cpf);

  Page<UserSchema> findByNamePageable(String name, Pageable pageable);

  Optional<UserSchema> findById(UUID uuid);

  UserSchema findUserByIdRequired(UUID userUuid);
}
