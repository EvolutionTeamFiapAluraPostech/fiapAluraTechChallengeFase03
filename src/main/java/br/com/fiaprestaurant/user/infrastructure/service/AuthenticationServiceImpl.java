package br.com.fiaprestaurant.user.infrastructure.service;

import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

  private final UserGateway userService;

  public AuthenticationServiceImpl(UserGateway userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userService.findByEmailRequired(username);
    return UserSchema.builder()
        .id(user.getId())
        .email(user.getEmail().address())
        .cpf(user.getCpf().getCpfNumber())
        .name(user.getName())
        .password(user.getPassword().passwordValue())
        .authorities(new ArrayList<>())
        .build();
  }
}
