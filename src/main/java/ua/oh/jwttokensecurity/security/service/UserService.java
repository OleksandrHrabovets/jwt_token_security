package ua.oh.jwttokensecurity.security.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.oh.jwttokensecurity.model.User;
import ua.oh.jwttokensecurity.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Optional<User> findByUsername(String username) {

    return userRepository.findByUsername(username);

  }

  public Optional<User> findById(Long id) {

    return userRepository.findById(id);

  }


}
