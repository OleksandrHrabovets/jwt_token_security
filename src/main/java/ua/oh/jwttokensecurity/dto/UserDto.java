package ua.oh.jwttokensecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

  private String username;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  private String firstName;

  private String lastName;

  private boolean enabled;

  private String email;

}