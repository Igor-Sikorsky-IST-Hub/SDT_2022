package com.ia01.yhnitii.shell.history.service;

import com.ia01.yhnitii.shell.history.repository.entity.History;

import java.util.List;

public interface HistoryService {

	History save(History history);

	List<History> findAll();

}
