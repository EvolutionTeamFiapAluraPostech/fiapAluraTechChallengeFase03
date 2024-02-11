package br.com.fiaprestaurant.user.infrastructure.security;

import br.com.fiaprestaurant.user.infrastructure.schema.UserSchema;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserFromSecurityContext {

  public UserSchema getUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.getPrincipal() != null) {
      if (authentication.getPrincipal() instanceof UserSchema) {
        return (UserSchema) authentication.getPrincipal();
      }
    }
    return null;
  }
}
