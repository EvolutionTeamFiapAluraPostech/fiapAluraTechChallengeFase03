package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_ID_REQUIRED;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_NAME_MAX_LENGTH;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_NAME_MIN_LENGTH;
import static br.com.fiaprestaurant.user.domain.messages.UserMessages.USER_NAME_REQUIRED;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import java.util.UUID;
import org.springframework.validation.FieldError;

public class User {

  public static final String USER_ID_FIELD = "ID";
  public static final String USER_NAME_FIELD = "name";
  private UUID id;
  private final String name;
  private final Email email;
  private final Password password;
  private final Cpf cpf;

  public User(String name, String email, String cpf, String password) {
    validateNameIsNullOrEmpty(name);
    validateNameMinLength(name);
    validateNameMaxLength(name);
    this.name = name;
    this.email = new Email(email);
    this.password = new Password(password);
    this.cpf = new Cpf(cpf);
  }

  public User(UUID id, String name, String email, String cpf, String password) {
    validateIdIsNull(id);
    validateNameIsNullOrEmpty(name);
    validateNameMinLength(name);
    validateNameMaxLength(name);
    this.id = id;
    this.name = name;
    this.email = new Email(email);
    this.cpf = new Cpf(cpf);
    this.password = new Password(password);
  }

  private void validateIdIsNull(UUID id) {
    if (id == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_ID_FIELD,
              USER_ID_REQUIRED));
    }
  }

  private void validateNameMaxLength(String name) {
    if (name.trim().length() > 500) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_NAME_FIELD,
              USER_NAME_MAX_LENGTH));
    }
  }
  private void validateNameMinLength(String name) {
    if (name.trim().length() < 2) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_NAME_FIELD,
              USER_NAME_MIN_LENGTH));
    }
  }

  private void validateNameIsNullOrEmpty(String name) {
    if (name == null || name.isEmpty()) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_NAME_FIELD,
              USER_NAME_REQUIRED));
    }
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public Cpf getCpf() {
    return cpf;
  }
}
