package br.com.fiaprestaurant.user.infrastructure.security;

import br.com.fiaprestaurant.user.application.gateway.UserGateway;
import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserGateway userService;

  public SecurityFilter(
      TokenService tokenService,
      UserGateway userService
  ) {
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var tokenJwt = getToken(request);
    if (tokenJwt != null) {
      var subject = tokenService.getSubject(tokenJwt);
      var user = userService.findByEmailRequired(subject);
      var userSchema = UserSchema.builder()
          .id(user.getId())
          .email(user.getEmail().address())
          .cpf(user.getCpf().getCpfNumber())
          .name(user.getName())
          .authorities(new ArrayList<>())
          .build();
      var authenticationToken = new UsernamePasswordAuthenticationToken(userSchema, null,
          userSchema.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      return authorizationHeader.replace("Bearer ", "").trim();
    }
    return null;
  }
}
