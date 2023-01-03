package com.ia01.yhnitii.shell.history.controller.facade.impl;

import com.ia01.yhnitii.shell.history.controller.dto.HistoryDto;
import com.ia01.yhnitii.shell.history.controller.facade.HistoryFacade;
import com.ia01.yhnitii.shell.history.controller.mapper.HistoryMapper;
import com.ia01.yhnitii.shell.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class HistoryFacadeImpl implements HistoryFacade {

	HistoryService historyService;

	HistoryMapper historyMapper;

	@Override
	public List<HistoryDto> findAll() {
		return historyService.findAll()
				.stream()
				.map(historyMapper::toDto)
				.toList();
	}
}
