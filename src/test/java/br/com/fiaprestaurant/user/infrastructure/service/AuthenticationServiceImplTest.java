package br.com.fiaprestaurant.user.infrastructure.service;

import static br.com.fiaprestaurant.shared.testData.user.UserTestData.DEFAULT_USER_EMAIL;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUser;
import static br.com.fiaprestaurant.shared.testData.user.UserTestData.createUserSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

  @Mock
  private UserService userService;
  @InjectMocks
  private AuthenticationServiceImpl UserDetailsService;

  @Test
  void shouldLoadUserByUsername() {
    var user = createUser();
    var userSchema = createUserSchema(user);

    when(userService.findByEmailRequired(userSchema.getEmail())).thenReturn(userSchema);
    var userFound = UserDetailsService.loadUserByUsername(userSchema.getUsername());

    assertThat(userFound).isNotNull();
    assertThat(userFound).usingRecursiveComparison().isEqualTo(userSchema);
  }

  @Test
  void shouldThrowExceptionWhenUserIsNotFound() {
    when(userService.findByEmailRequired(DEFAULT_USER_EMAIL)).thenReturn(null);
    var userFound = UserDetailsService.loadUserByUsername(DEFAULT_USER_EMAIL);

    assertThat(userFound).isNull();
  }
}