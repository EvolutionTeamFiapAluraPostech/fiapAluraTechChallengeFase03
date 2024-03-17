package br.com.fiaprestaurant.shared.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtils {

  private SpringSecurityUtils() {
  }

  public static String getCurrentPrincipalUsername() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

}
