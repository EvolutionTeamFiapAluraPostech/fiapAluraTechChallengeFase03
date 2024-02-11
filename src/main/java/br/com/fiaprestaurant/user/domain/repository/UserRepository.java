package br.com.fiaprestaurant.user.domain.repository;

import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

  Optional<UserSchema> findByEmail(String email);

  Optional<UserSchema> findByCpf(String cpf);

  Page<UserSchema> findByNameLikeIgnoreCase(String name, Pageable pageable);

}
