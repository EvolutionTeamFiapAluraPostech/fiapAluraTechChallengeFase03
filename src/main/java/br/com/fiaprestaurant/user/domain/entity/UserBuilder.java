package br.com.fiaprestaurant.user.domain.entity;

import java.util.UUID;

public class UserBuilder {

  private UUID id;
  private String name;
  private String email;
  private String password;
  private String cpf;

  public UserBuilder setId(UUID id) {
    this.id = id;
    return this;
  }

  public UserBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public UserBuilder setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder setCpf(String cpf) {
    this.cpf = cpf;
    return this;
  }

  public User createUser() {
    return new User(id, name, email, cpf, password);
  }

  public User updateUser() {
    return new User(name, email, cpf);
  }
}
