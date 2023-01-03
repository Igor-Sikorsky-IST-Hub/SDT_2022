package com.ia01.yhnitii.shell.history.controller.facade;

import com.ia01.yhnitii.shell.history.controller.dto.HistoryDto;

import java.util.List;

public interface HistoryFacade {

	List<HistoryDto> findAll();

}
