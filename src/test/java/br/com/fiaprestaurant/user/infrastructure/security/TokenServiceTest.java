package br.com.fiaprestaurant.user.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiaprestaurant.shared.testData.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

  @InjectMocks
  private TokenService tokenService;

  @Test
  void shouldGenerateToken() {
    ReflectionTestUtils.setField(tokenService, "secret", "Fi@p-@lur@-2ADJT");
    var user = UserTestData.createUser();
    var userSchema = UserTestData.createUserSchema(user);

    var token = tokenService.generateToken(userSchema);

    assertThat(token).isNotNull().isNotEmpty();
  }

  @Test
  void shouldGetSubject() {
    ReflectionTestUtils.setField(tokenService, "secret", "Fi@p-@lur@-2ADJT");
    var user = UserTestData.createUser();
    var userSchema = UserTestData.createUserSchema(user);

    var token = tokenService.generateToken(userSchema);
    var subject = tokenService.getSubject(token);

    assertThat(subject).isNotNull().isNotEmpty();
  }

}