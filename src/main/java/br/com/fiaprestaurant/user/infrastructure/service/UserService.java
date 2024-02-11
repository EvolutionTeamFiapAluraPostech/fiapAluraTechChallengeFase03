package br.com.fiaprestaurant.user.infrastructure.service;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_NOT_FOUND;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_ID_NOT_FOUND;

import br.com.fiaprestaurant.shared.exception.NoResultException;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.infrastructure.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class UserService implements br.com.fiaprestaurant.user.domain.service.UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserSchema save(UserSchema userSchema) {
    return userRepository.save(userSchema);
  }

  public Page<UserSchema> getAllUsersPaginated(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public Optional<UserSchema> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public UserSchema findByEmailRequired(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(
            () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "email",
                USER_EMAIL_NOT_FOUND.formatted(email))));
  }

  public Optional<UserSchema> findByCpf(String cpf) {
    return userRepository.findByCpf(cpf);
  }

  public Page<UserSchema> findByNamePageable(String name, Pageable pageable) {
    return userRepository.findByNameLikeIgnoreCase(name, pageable);
  }

  public Optional<UserSchema> findById(UUID uuid) {
    return userRepository.findById(uuid);
  }

  public UserSchema findUserByIdRequired(UUID userUuid) {
    return userRepository.findById(userUuid)
        .orElseThrow(
            () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "cpf",
                USER_ID_NOT_FOUND.formatted(userUuid))));
  }
}
