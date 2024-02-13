package br.com.fiaprestaurant.shared.testData.user;

import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.UUID;

public final class UserTestData {

  public static final UUID DEFAULT_USER_UUID = UUID.randomUUID();
  public static final String DEFAULT_USER_UUID_FROM_STRING = DEFAULT_USER_UUID.toString();
  public static final String DEFAULT_USER_NAME = "Morpheus";
  public static final String DEFAULT_USER_EMAIL = "morpheus@matrix.com";
  public static final String DEFAULT_USER_CPF = "11955975094";
  public static final UUID ALTERNATIVE_USER_UUID = UUID.randomUUID();
  public static final String ALTERNATIVE_USER_NAME = "Neo";
  public static final String ALTERNATIVE_USER_EMAIL = "neo@matrix.com";
  public static final String ALTERNATIVE_USER_CPF = "79693503058";
  public static final String DEFAULT_USER_PASSWORD = "@Bcd1234";

  public static final String USER_TEMPLATE_INPUT = """
      {"name": "%s", "email": "%s", "cpf": "%s", "password": "%s"}""";

  public static final String USER_INPUT = USER_TEMPLATE_INPUT.formatted(DEFAULT_USER_NAME,
      DEFAULT_USER_EMAIL, DEFAULT_USER_CPF, DEFAULT_USER_PASSWORD);

  public static final String ALTERNATIVE_USER_INPUT = USER_TEMPLATE_INPUT.formatted(
      ALTERNATIVE_USER_NAME, ALTERNATIVE_USER_EMAIL, ALTERNATIVE_USER_CPF, DEFAULT_USER_PASSWORD);

  public static final String USER_TEMPLATE_UPDATE = """
      {"name": "%s", "email": "%s", "cpf": "%s"}""";

  public static final String USER_UPDATE = USER_TEMPLATE_UPDATE.formatted(ALTERNATIVE_USER_NAME,
      ALTERNATIVE_USER_EMAIL, ALTERNATIVE_USER_CPF);


  public static User createUser() {
    return new User(DEFAULT_USER_UUID, ALTERNATIVE_USER_NAME, ALTERNATIVE_USER_EMAIL,
        ALTERNATIVE_USER_CPF, DEFAULT_USER_PASSWORD);
  }

  public static UserSchema createUserSchema(User user) {
    return UserSchema.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail().address())
        .cpf(user.getCpf().getCpfNumber())
        .password(user.getPassword().getPasswordValue())
        .build();
  }

  public static UserSchema createNewUserSchema(User user) {
    return UserSchema.builder()
        .name(user.getName())
        .email(user.getEmail().address())
        .cpf(user.getCpf().getCpfNumber())
        .password(user.getPassword().getPasswordValue())
        .build();
  }

  public static User createNewUser() {
    return new User(DEFAULT_USER_NAME, DEFAULT_USER_EMAIL, DEFAULT_USER_CPF, DEFAULT_USER_PASSWORD);
  }

  public static User createNewUserWithId() {
    return new User(DEFAULT_USER_UUID, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL, DEFAULT_USER_CPF,
        DEFAULT_USER_PASSWORD);
  }

  public static User createAlternativeUser() {
    return new User(ALTERNATIVE_USER_UUID, ALTERNATIVE_USER_NAME, ALTERNATIVE_USER_EMAIL,
        ALTERNATIVE_USER_CPF, DEFAULT_USER_PASSWORD);
  }

}
