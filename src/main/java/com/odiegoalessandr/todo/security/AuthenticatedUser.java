package com.odiegoalessandr.todo.security;

import com.odiegoalessandr.todo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public record AuthenticatedUser(
  UUID id,
  String username,
  Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

  public static AuthenticatedUser from(SecurityUserDetails user) {
    return new AuthenticatedUser(
      user.getId(),
      user.getUsername(),
      AuthorityUtils.createAuthorityList("ROLE_USER")
    );
  }

  public UUID getId() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }
}
