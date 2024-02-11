package br.com.fiaprestaurant.user.domain.entity;

public record Cpf(String cpf) {

  public Cpf {
    if (cpf == null || !cpf.matches("\\d{3}\\d{3}\\d{3}\\d{2}")) {
      throw new IllegalArgumentException("Invalid CPF.");
    }
    if (cpf.matches("(\\d)\\1*")) {
      throw new IllegalArgumentException("Invalid CPF.");
    }
  }
}
