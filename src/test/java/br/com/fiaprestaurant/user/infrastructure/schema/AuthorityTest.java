package br.com.fiaprestaurant.user.infrastructure.schema;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class AuthorityTest {

  @Test
  void shouldCreateAuthorityWithNoConstructorArguments() {
    var authority = new Authority();
    authority.setId(UUID.randomUUID());
    authority.setName("Teste");

    assertThat(authority).isNotNull();
    assertThat(authority.getId()).isNotNull();
    assertThat(authority.getName()).isNotNull();
    assertThat(authority.getAuthority()).isNotNull();
  }

  @Test
  void shouldCreateAuthorityWithConstructorArguments() {
    var authority = new Authority(UUID.randomUUID(), "Teste");

    assertThat(authority).isNotNull();
    assertThat(authority.getId()).isNotNull();
    assertThat(authority.getName()).isNotNull();
    assertThat(authority.getAuthority()).isNotNull().isNotEmpty().isEqualTo("Teste");
  }

  @Test
  void shouldVerifyIfAuthorityIsEqualToOtherAuthority() {
    var authority = new Authority(UUID.randomUUID(), "Teste");
    var authority2 = new Authority(UUID.randomUUID(), "Teste2");

    assertThat(authority).isNotNull();
    assertThat(authority.getId()).isNotNull();
    assertThat(authority.getName()).isNotNull();
    assertThat(authority.getAuthority()).isNotNull().isNotEmpty().isEqualTo("Teste");
    assertThat(authority2).isNotNull();
    assertThat(authority2.getId()).isNotNull();
    assertThat(authority2.getName()).isNotNull();
    assertThat(authority2.getAuthority()).isNotNull().isNotEmpty().isEqualTo("Teste2");
    assertThat(authority).isNotEqualTo(authority2);
  }

}