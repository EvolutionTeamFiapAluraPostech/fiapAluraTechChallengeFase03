package br.com.fiaprestaurant.shared.testData.user;

public final class AuthenticateTestData {

  public static final String DEFAULT_USER_PASSWORD = "@Bcd1234";
  public static final String DEFAULT_USER_AUTHENTICATE_EMAIL = "thomas.anderson@itcompany.com";
  public static final String URL_AUTHENTICATE = "/authenticate";

  public static final String DEFAULT_USER_AUTHENTICATE_REQUEST_BODY_TEMPLATE = """
      {"email": "%s", "password": "%s"}
      """;

  public static final String DEFAULT_USER_AUTHENTICATE_REQUEST_BODY =
      DEFAULT_USER_AUTHENTICATE_REQUEST_BODY_TEMPLATE.formatted(DEFAULT_USER_AUTHENTICATE_EMAIL,
          DEFAULT_USER_PASSWORD);

  private AuthenticateTestData() {
  }
}
