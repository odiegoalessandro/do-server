package com.odiegoalessandr.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends AuditableEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Size(max = 100)
  @Column(
    nullable = false,
    unique = true,
    length = 100
  )
  private String username;

  @NotBlank
  @Email
  @Size(max = 255)
  @Column(
    nullable = false,
    unique = true,
    length = 255
  )
  private String email;

  @NotBlank
  @Size(max = 255)
  @Column(
    name = "password_hash",
    nullable = false,
    length = 255
  )
  private String passwordHash;

  @NotBlank
  @Size(max = 255)
  @Column(
    name = "full_name",
    nullable = false,
    length = 255
  )
  private String fullName;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList("ROLE_USER");
  }

  @Override
  public String getPassword() {
    return passwordHash;
  }
}