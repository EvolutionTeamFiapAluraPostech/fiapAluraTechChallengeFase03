package br.com.fiaprestaurant.user.domain.entity;

public record Email(String address) {

  public Email {
    if (address == null || !address.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      throw new IllegalArgumentException("Invalid email address.");
    }
  }
}
