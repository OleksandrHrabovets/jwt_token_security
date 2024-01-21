package ua.oh.jwttokensecurity.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.oh.jwttokensecurity.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}
