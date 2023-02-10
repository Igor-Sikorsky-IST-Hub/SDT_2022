package com.ia01.yhnitii.shell.history.controller;

import com.ia01.yhnitii.shell.common.logging.LogExecutionTime;
import com.ia01.yhnitii.shell.history.controller.dto.HistoryDto;
import com.ia01.yhnitii.shell.history.controller.facade.HistoryFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "history")
@RestController
@RequestMapping("/api/v1/public/history")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class HistoryController {

	HistoryFacade historyFacade;

	@LogExecutionTime
	@GetMapping
	public ResponseEntity<List<HistoryDto>> findAll() {
		List<HistoryDto> response = historyFacade.findAll();
		return ResponseEntity.ok(response);
	}

}
