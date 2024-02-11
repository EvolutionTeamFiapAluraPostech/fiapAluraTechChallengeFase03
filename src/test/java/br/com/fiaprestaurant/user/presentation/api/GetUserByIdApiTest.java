package br.com.fiaprestaurant.user.presentation.api;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createNewUserSchema;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.api.JsonUtil;
import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.presentation.dto.UserOutputDto;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
@DatabaseTest
class GetUserByIdApiTest {

  private static final String URL_USERS = "/users/";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public GetUserByIdApiTest(
      MockMvc mockMvc,
      EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private UserSchema createAndPersistNewUser(User user) {
    var userSchema = createNewUserSchema(user);
    return entityManager.merge(userSchema);
  }

  @Test
  void shoudReturnUserWhenUserExists() throws Exception {
    var user = createUser();
    var userSchema = createAndPersistNewUser(user);
    var userOutputDtoExpected = new UserOutputDto(userSchema.getId().toString(),
        userSchema.getName(),
        userSchema.getEmail(), userSchema.getCpf());

    var request = get(URL_USERS + userSchema.getId());
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var userDtoFound = JsonUtil.fromJson(contentAsString, UserOutputDto.class);
    assertThat(userDtoFound).usingRecursiveComparison().isEqualTo(userOutputDtoExpected);
  }

  @Test
  void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
    var request = MockMvcRequestBuilders.get(URL_USERS + UUID.randomUUID());
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBadRequestWhenUserUuidIsInvalid() throws Exception {
    var request = MockMvcRequestBuilders.get(URL_USERS + "aaa");
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }
}
