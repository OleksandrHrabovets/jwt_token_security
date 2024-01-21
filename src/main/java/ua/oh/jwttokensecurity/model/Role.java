package ua.oh.jwttokensecurity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  Set<User> users;

}