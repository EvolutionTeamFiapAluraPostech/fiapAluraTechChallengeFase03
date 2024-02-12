package br.com.fiaprestaurant.user.application.validator;

public interface UserEmailAlreadyRegisteredInOtherUserValidator {

  void validate(String userUuid, String email);
}
