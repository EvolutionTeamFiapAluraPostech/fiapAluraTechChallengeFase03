package br.com.fiaprestaurant.user.domain.entity;

import static br.com.fiaprestaurant.user.domain.messages.CpfMessages.CPF_FIELD_NAME;
import static br.com.fiaprestaurant.user.domain.messages.CpfMessages.CPF_INVALID;

import br.com.fiaprestaurant.shared.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record Cpf(String cpf) {

  public Cpf {
    if (cpf == null || !cpf.matches("\\d{3}\\d{3}\\d{3}\\d{2}")) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), CPF_FIELD_NAME, CPF_INVALID));
    }
    if (cpf.matches("(\\d)\\1*")) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), CPF_FIELD_NAME, CPF_INVALID));
    }
  }
}
