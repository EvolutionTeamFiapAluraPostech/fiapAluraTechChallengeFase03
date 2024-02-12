package br.com.fiaprestaurant.user.presentation.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiaprestaurant.shared.annotation.DatabaseTest;
import br.com.fiaprestaurant.shared.annotation.IntegrationTest;
import br.com.fiaprestaurant.shared.api.JsonUtil;
import br.com.fiaprestaurant.shared.api.PageUtil;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import br.com.fiaprestaurant.user.presentation.dto.UserContent;
import br.com.fiaprestaurant.user.presentation.dto.UserOutputDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetAllUsersPaginatedApiTest {

  private static final String URL_USERS = "/users";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  public GetAllUsersPaginatedApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private UserSchema findUser() {
    return (UserSchema) entityManager
        .createQuery("SELECT u FROM UserSchema u WHERE email = :email")
        .setParameter("email", "thomas.anderson@itcompany.com")
        .getSingleResult();
  }

  @Test
  void shouldReturnAllUsersWhenUsersExist() throws Exception {
    var user = findUser();
    var userPage = PageUtil.generatePageOfUser(user);
    var userExpected = UserOutputDto.toPage(userPage);

    var request = get(URL_USERS);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.content", hasSize(1)))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var users = JsonUtil.fromJson(contentAsString, UserContent.class);
    assertThat(users.getContent()).usingRecursiveComparison().isEqualTo(userExpected);
  }
}
