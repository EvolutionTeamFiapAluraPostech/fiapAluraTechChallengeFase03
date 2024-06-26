package br.com.fiaprestaurant.user.presentation.api;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.ALTERNATIVE_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetUserByEmailApiTest {

  private static final String URL_USERS = "/users/email/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetUserByEmailApiTest(
      MockMvc mockMvc,
      EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private UserSchema createAndPersistNewUser() {
    var user = createUser();
    var userSchema = createNewUserSchema(user);
    return entityManager.merge(userSchema);
  }

  @Test
  void shouldReturnUserWhenUserExists() throws Exception {
    var user = createAndPersistNewUser();

    var request = get(URL_USERS + user.getEmail());
    mockMvc.perform(request)
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
    var request = get(URL_USERS + ALTERNATIVE_USER_EMAIL);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @ParameterizedTest
  @ValueSource(strings = {"email.domain.com", "@", "1234", "x@y.z"})
  void shouldReturnBadRequestWhenUserEmailIsInvalid(String email) throws Exception {
    var request = get(URL_USERS + email);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }
}
