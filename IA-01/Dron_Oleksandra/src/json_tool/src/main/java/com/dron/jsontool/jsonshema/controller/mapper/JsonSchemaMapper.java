package com.dron.jsontool.jsonshema.controller.mapper;

import com.dron.jsontool.jsonhistory.controller.mapper.JsonHistoryMapper;
import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = JsonHistoryMapper.class)
public interface JsonSchemaMapper {

	JsonSchemaDto toDto(JsonSchema entity);

	@Named("toDtoWithoutEntities")
	@Mapping(target = "owner", ignore = true)
	@Mapping(target = "previousHistory", ignore = true)
	@Mapping(target = "currentHistory", ignore = true)
	JsonSchemaDto toDtoWithoutEntities(JsonSchema entity);

	JsonSchema toEntity(JsonSchemaDto dto);
}
