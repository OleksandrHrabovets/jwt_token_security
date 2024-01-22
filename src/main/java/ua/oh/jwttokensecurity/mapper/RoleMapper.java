package ua.oh.jwttokensecurity.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ua.oh.jwttokensecurity.dto.RoleDto;
import ua.oh.jwttokensecurity.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  RoleDto map(Role role);

  @InheritInverseConfiguration
  Role map(RoleDto roleDto);

}