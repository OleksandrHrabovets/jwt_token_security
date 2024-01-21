package ua.oh.jwttokensecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.oh.jwttokensecurity.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
