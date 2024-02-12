package br.com.fiaprestaurant.user.application.validator;

public interface UserCpfAlreadyRegisteredInOtherUserValidator {

  void validate(String userUuid, String cpf);
}
