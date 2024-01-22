package ua.oh.jwttokensecurity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ua.oh.jwttokensecurity.dto.UserDto;
import ua.oh.jwttokensecurity.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto map(User user);

  @InheritInverseConfiguration
  User map(UserDto userDto);

}