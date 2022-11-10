package com.dron.jsontool.user.controller.mapper;

import com.dron.jsontool.jsonshema.controller.mapper.JsonSchemaMapper;
import com.dron.jsontool.user.controller.dto.UserDto;
import com.dron.jsontool.user.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = JsonSchemaMapper.class)
public interface UserMapper {

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "jsonSchemas", source = "jsonSchemas", qualifiedByName = "toDtoWithoutEntities")
	UserDto toDto(User user);

	User toEntity(UserDto user);

}
