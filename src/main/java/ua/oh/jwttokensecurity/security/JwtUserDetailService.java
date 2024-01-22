package ua.oh.jwttokensecurity.security;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.oh.jwttokensecurity.model.User;
import ua.oh.jwttokensecurity.repository.RoleRepository;
import ua.oh.jwttokensecurity.security.jwt.JwtUser;
import ua.oh.jwttokensecurity.security.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

  private final UserService userService;
  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    JwtUser jwtUser = new JwtUser(user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getFirstName(),
        user.getLastName(),
        user.isEnabled(),
        user.getEmail(),
        getAuthorities(user),
        user.getUpdatedAt()
    );
    log.info("Jwt user with username {} created successfully", username);
    return jwtUser;
  }

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    return user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .toList();
  }

}