package br.com.fiaprestaurant.user.infrastructure.service;

import br.com.fiaprestaurant.user.application.gateway.UserGateway;
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
    return userService.findByEmailRequired(username);
  }
}
