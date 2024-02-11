package br.com.fiaprestaurant.shared.domain.entity;

public interface AuditableEntity {

  void setCreatedBy(String email);
  void setUpdatedBy(String email);
}
