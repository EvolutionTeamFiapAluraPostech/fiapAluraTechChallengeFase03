package br.com.fiaprestaurant.user.domain.entity;

import java.util.UUID;

public class User {

  private UUID id;
  private final String name;
  private final Email email;
  private final Password password;
  private final Cpf cpf;

  public User(String name, String email, String cpf, String password) {
    validateNameIsNullOrEmpty(name);
    validateNameLength(name);
    this.name = name;
    this.email = new Email(email);
    this.password = new Password(password);
    this.cpf = new Cpf(cpf);
  }

  public User(UUID id, String name, String email, String cpf, String password) {
    validateIdIsNull(id);
    validateNameIsNullOrEmpty(name);
    validateNameLength(name);
    this.id = id;
    this.name = name;
    this.email = new Email(email);
    this.cpf = new Cpf(cpf);
    this.password = new Password(password);
  }

  private static void validateIdIsNull(UUID id) {
    if (id == null) {
      throw new IllegalArgumentException("ID is required.");
    }
  }

  private static void validateNameLength(String name) {
    if (name.trim().length() > 500) {
      throw new IllegalArgumentException("Max name length is 500 characters.");
    }
  }

  private static void validateNameIsNullOrEmpty(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name is required");
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
