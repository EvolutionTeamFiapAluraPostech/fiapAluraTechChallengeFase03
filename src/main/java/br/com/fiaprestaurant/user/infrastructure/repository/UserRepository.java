package br.com.fiaprestaurant.user.infrastructure.repository;

import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserSchema, UUID>,
    br.com.fiaprestaurant.user.domain.repository.UserRepository {

  Optional<UserSchema> findByEmail(String email);

  Optional<UserSchema> findByCpf(String cpf);

  Page<UserSchema> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
