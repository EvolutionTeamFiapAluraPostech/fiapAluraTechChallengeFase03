package br.com.fiaprestaurant.user.infrastructure.usecase;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiaprestaurant.user.domain.entity.User;
import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class GetAllUsersInteractorTest {

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 1;

  @Mock
  private UserGateway userService;
  @InjectMocks
  private GetAllUsersInteractor getAllUsersInteractor;

  @Test
  void shouldGetAllUsersWhenUsersExists() {
    var user = createUser();
    var users = List.of(user);
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = users.size();
    var page = new PageImpl<>(users, pageable, size);

    when(userService.getAllUsersPaginated(pageable)).thenReturn(page);
    var usersFound = getAllUsersInteractor.execute(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }

  @Test
  void shouldReturnEmptyPageWhenDoesNotExistAnyUserSaved() {
    var users = new ArrayList<User>();
    var pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    var size = 0;
    var page = new PageImpl<>(users, pageable, size);

    when(userService.getAllUsersPaginated(pageable)).thenReturn(page);
    var usersFound = getAllUsersInteractor.execute(pageable);

    assertThat(usersFound).isNotNull();
    assertThat(usersFound.getSize()).isEqualTo(PAGE_SIZE);
    assertThat(usersFound.getTotalPages()).isEqualTo(size);
    assertThat(usersFound.getTotalElements()).isEqualTo(size);
  }
}
