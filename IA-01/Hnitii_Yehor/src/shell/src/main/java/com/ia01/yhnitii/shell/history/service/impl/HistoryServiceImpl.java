package com.ia01.yhnitii.shell.history.service.impl;

import com.ia01.yhnitii.shell.history.repository.HistoryRepository;
import com.ia01.yhnitii.shell.history.repository.entity.History;
import com.ia01.yhnitii.shell.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class HistoryServiceImpl implements HistoryService {

	HistoryRepository historyRepository;

	@Override
	public History save(History history) {
		return historyRepository.save(history);
	}

	@Override
	public List<History> findAll() {
		return historyRepository.findAll(Sort.by(DESC, "executed"));
	}
}
