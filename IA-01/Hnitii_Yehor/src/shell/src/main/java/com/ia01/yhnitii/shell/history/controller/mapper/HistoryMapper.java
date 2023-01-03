package com.ia01.yhnitii.shell.history.controller.mapper;

import com.ia01.yhnitii.shell.history.controller.dto.HistoryDto;
import com.ia01.yhnitii.shell.history.repository.entity.History;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

	HistoryDto toDto(History history);

}
