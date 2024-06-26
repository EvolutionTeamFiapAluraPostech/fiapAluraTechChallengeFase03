package br.com.fiaprestaurant.user.presentation.api;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.ALTERNATIVE_USER_CPF;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.ALTERNATIVE_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.ALTERNATIVE_USER_NAME;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.USER_TEMPLATE_UPDATE;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.USER_UPDATE;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PutUserApiTest {

  private static final String URL_USERS = "/users/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PutUserApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private UserSchema createAndPersistUser() {
    var user = createUser();
    var userSchema = createNewUserSchema(user);
    return entityManager.merge(userSchema);
  }

  private UserSchema createAndPersistUserWithDifferentAttributes() {
    var user = UserSchema.builder()
        .name(ALTERNATIVE_USER_NAME)
        .email(ALTERNATIVE_USER_EMAIL)
        .cpf(ALTERNATIVE_USER_CPF)
        .password(DEFAULT_USER_PASSWORD)
        .build();
    return entityManager.merge(user);
  }

  private UserSchema findUser() {
    return (UserSchema) entityManager
        .createQuery("SELECT u FROM UserSchema u WHERE email = :email")
        .setParameter("email", "thomas.anderson@itcompany.com")
        .getSingleResult();
  }

  @Test
  void shouldUpdateUser() throws Exception {
    var user = createAndPersistUser();

    var request = put(URL_USERS + user.getId())
        .contentType(APPLICATION_JSON)
        .content(USER_UPDATE);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isAccepted())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.id").toString();
    var userFound = entityManager.find(UserSchema.class, UUID.fromString(id));
    assertThat(userFound).isNotNull();
    assertThat(userFound.getName()).isEqualTo(ALTERNATIVE_USER_NAME);
    assertThat(userFound.getEmail()).isEqualTo(ALTERNATIVE_USER_EMAIL);
    assertThat(userFound.getPassword()).isEqualTo(DEFAULT_USER_PASSWORD);
  }

  @Test
  void shouldReturnNotFoundWhenUserWasNotFoundToUpdate() throws Exception {
    var userUuid = UUID.randomUUID();

    var request = put(URL_USERS + userUuid)
        .contentType(APPLICATION_JSON)
        .content(USER_UPDATE);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenUserUuidIsInvalid() throws Exception {
    var userUuid = "aaa";

    var request = put(URL_USERS + userUuid)
        .contentType(APPLICATION_JSON)
        .content(USER_UPDATE);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenUserEmailAlreadyExistsInOtherUser() throws Exception {
    var firstUser = findUser();
    var secondUser = createAndPersistUserWithDifferentAttributes();

    var request = put(URL_USERS + secondUser.getId())
        .contentType(APPLICATION_JSON)
        .content(USER_TEMPLATE_UPDATE.formatted(firstUser.getName(), firstUser.getEmail(),
            firstUser.getCpf()));
    mockMvc.perform(request)
        .andExpect(status().isConflict());
  }

  @Test
  void shouldReturnBadRequestWhenUserCpfAlreadyExistsInOtherUser() throws Exception {
    var firstUser = findUser();
    var secondUser = createAndPersistUserWithDifferentAttributes();

    var request = put(URL_USERS + secondUser.getId())
        .contentType(APPLICATION_JSON)
        .content(USER_TEMPLATE_UPDATE.formatted(firstUser.getName(), "neo@matrix.com",
            firstUser.getCpf()));
    mockMvc.perform(request)
        .andExpect(status().isConflict());
  }
}
