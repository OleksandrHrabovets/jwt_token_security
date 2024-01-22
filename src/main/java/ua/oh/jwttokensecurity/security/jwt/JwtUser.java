package ua.oh.jwttokensecurity.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
@ToString(exclude = "password")
public class JwtUser implements UserDetails {

  private final Long id;
  private final String username;
  @JsonIgnore
  private final String password;
  private final String firstName;
  private final String lastName;
  private final boolean enabled;
  private final String email;
  private final Collection<? extends GrantedAuthority> authorities;
  private final LocalDateTime updatedAt;

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

}