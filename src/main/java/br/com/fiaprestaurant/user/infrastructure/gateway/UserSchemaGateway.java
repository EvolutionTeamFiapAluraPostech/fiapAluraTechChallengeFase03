package br.com.fiaprestaurant.user.infrastructure.gateway;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_EMAIL_NOT_FOUND;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_ID_NOT_FOUND;

import br.com.fiaprestaurant.shared.infrastructure.exception.NoResultException;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import br.com.fiaprestaurant.user.infrastructure.repository.UserSchemaRepository;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class UserSchemaGateway implements UserGateway {

  private final UserSchemaRepository userRepository;

  public UserSchemaGateway(UserSchemaRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User save(User user) {
    var userSchema = createFrom(user);
    var userSchemaSaved = userRepository.save(userSchema);
    return userSchemaSaved.getUser();
  }

  private static UserSchema createFrom(User user) {
    return UserSchema.builder()
        .name(user.getName())
        .email(user.getEmail().address())
        .cpf(user.getCpf().getCpfNumber())
        .password(user.getPassword().getPasswordValue())
        .build();
  }

  @Override
  public User update(UUID id, User user) {
    var userSchema = this.findUserSchemaByIdRequired(id);
    updateAttributesToUpdate(userSchema, user);
    var userSchemaSaved = userRepository.save(userSchema);
    return userSchemaSaved.getUser();
  }

  private static void updateAttributesToUpdate(UserSchema userSchema, User user) {
    userSchema.setName(user.getName());
    userSchema.setEmail(user.getEmail().address());
    userSchema.setCpf(user.getCpf().getCpfNumber());
  }

  @Override
  public void delete(UUID id) {
    var userSchema = this.findUserSchemaByIdRequired(id);
    userSchema.setDeleted(true);
    userRepository.save(userSchema);
  }

  public UserSchema findUserSchemaByIdRequired(UUID userUuid) {
    return userRepository.findById(userUuid)
        .orElseThrow(
            () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "ID",
                USER_ID_NOT_FOUND.formatted(userUuid))));
  }

  public Page<User> getAllUsersPaginated(Pageable pageable) {
    var userSchemaPage = userRepository.findAll(pageable);
    return getUsersFrom(userSchemaPage);
  }

  public Page<User> findByNamePageable(String name, Pageable pageable) {
    var userSchemaPage = userRepository.findByNameLikeIgnoreCase(name, pageable);
    return getUsersFrom(userSchemaPage);
  }

  private static Page<User> getUsersFrom(Page<UserSchema> userSchemaPage) {
    return userSchemaPage.map(UserSchema::getUser);
  }

  public Optional<User> findByEmail(String email) {
    var userSchemaOptional = userRepository.findByEmail(email);
    return userSchemaOptional.map(UserSchema::getUser);
  }

  public UserSchema findByEmailRequired(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(
            () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "email",
                USER_EMAIL_NOT_FOUND.formatted(email))));
  }

  public Optional<User> findByCpf(String cpf) {
    var userSchemaOptional = userRepository.findByCpf(cpf);
    return userSchemaOptional.map(UserSchema::getUser);
  }

  public Optional<User> findById(UUID uuid) {
    var userSchemaOptional = userRepository.findById(uuid);
    return userSchemaOptional.map(UserSchema::getUser);
  }

  public User findUserByIdRequired(UUID userUuid) {
    var userSchema = userRepository.findById(userUuid)
        .orElseThrow(
            () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "ID",
                USER_ID_NOT_FOUND.formatted(userUuid))));
    return userSchema.getUser();
  }

}
