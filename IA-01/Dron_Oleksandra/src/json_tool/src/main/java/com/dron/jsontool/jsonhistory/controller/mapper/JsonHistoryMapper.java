package com.dron.jsontool.jsonhistory.controller.mapper;

import com.dron.jsontool.jsonhistory.controller.dto.JsonHistoryDto;
import com.dron.jsontool.jsonhistory.repository.entity.JsonHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JsonHistoryMapper {

	@Mapping(target = "previous.owner", ignore = true)
	@Mapping(target = "previous.previousHistory", ignore = true)
	@Mapping(target = "previous.currentHistory", ignore = true)
	@Mapping(target = "current.owner", ignore = true)
	@Mapping(target = "current.previousHistory", ignore = true)
	@Mapping(target = "current.currentHistory", ignore = true)
	JsonHistoryDto toDto(JsonHistory entity);

	JsonHistory toEntity(JsonHistoryDto dto);
}
