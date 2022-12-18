package com.dron.jsontool.jsonshema.controller.mapper;

import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JsonSchemaMapper {

	JsonSchemaDto toDto(JsonSchema entity);

	JsonSchema toEntity(JsonSchemaDto dto);

}
