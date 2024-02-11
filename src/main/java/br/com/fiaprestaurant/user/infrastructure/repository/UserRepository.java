package br.com.fiaprestaurant.user.infrastructure.repository;

import br.com.fiaprestaurant.user.infrastructure.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID>,
    br.com.fiaprestaurant.user.domain.repository.UserRepository {

  Optional<User> findByEmail(String email);

  Optional<User> findByCpf(String cpf);

  Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
