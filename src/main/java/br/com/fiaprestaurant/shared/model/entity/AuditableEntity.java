package br.com.fiaprestaurant.shared.model.entity;

public interface AuditableEntity {

  void setCreatedBy(String email);
  void setUpdatedBy(String email);
}
