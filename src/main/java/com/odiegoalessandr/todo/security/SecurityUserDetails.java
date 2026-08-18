package com.odiegoalessandr.todo.security;

import com.odiegoalessandr.todo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public record SecurityUserDetails(
  UUID id,
  String username,
  String password,
  Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

  public static SecurityUserDetails from(User user) {
    return new SecurityUserDetails(
      user.getId(),
      user.getUsername(),
      user.getPasswordHash(),
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
