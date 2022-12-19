package com.dron.jsontool.jsonhistory.service;

import com.dron.jsontool.jsonhistory.repository.JsonHistoryRepository;
import com.dron.jsontool.jsonhistory.repository.entity.JsonHistory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JsonHistoryServiceImpl implements JsonHistoryService {

	JsonHistoryRepository jsonHistoryRepository;

	@Override
	public JsonHistory save(JsonHistory jsonHistory) {
		return jsonHistoryRepository.save(jsonHistory);
	}
}
